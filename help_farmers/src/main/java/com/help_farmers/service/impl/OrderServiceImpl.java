package com.help_farmers.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.help_farmers.common.exception.AppException;
import com.help_farmers.common.result.ResponseCode;
import com.help_farmers.common.utils.AssertUtil;
import com.help_farmers.common.utils.SnowFlakeUtil;
import com.help_farmers.mapper.OrderMapper;
import com.help_farmers.model.domain.*;
import com.help_farmers.model.query.AccountQuery;
import com.help_farmers.model.query.AuditQuery;
import com.help_farmers.model.query.OrderQuery;
import com.help_farmers.model.query.SaveOrderQuery;
import com.help_farmers.model.vo.ListVo;
import com.help_farmers.model.vo.OrderVo;
import com.help_farmers.service.IJxcInventoryService;
import com.help_farmers.service.ILogService;
import com.help_farmers.service.IOrderDetailService;
import com.help_farmers.service.IOrderService;
import com.help_farmers.service.websocket.HPWebSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-25
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private IOrderDetailService orderDetailService;

    @Resource(name = "StringRedisTemplate")
    private RedisTemplate redisTemplate;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private IJxcInventoryService inventoryService;

    @Resource
    private SnowFlakeUtil snowFlakeUtil;

    @Resource
    private ILogService logService;

    @Resource
    private HPWebSocket hpWebSocket;

    @Override
    public ListVo<OrderVo> orderList(OrderQuery orderQuery, LoginUser2 current) throws JsonProcessingException {

        // 创建分页查询的page对象
        IPage<Order> page = new Page<>(orderQuery.getPageno(), orderQuery.getPagesize());

        /**
         * 将日期格式化与数据库中的日期进行对比
         * 先判断非空
         */
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String beginTime = null;
        String endTime = null;
        if (ObjectUtil.isNotNull(orderQuery.getBeginTime())) {
            beginTime = dateFormat.format(orderQuery.getBeginTime());
            log.info("beginTime: {}", beginTime);

        }
        if (ObjectUtil.isNotNull(orderQuery.getEndTime())) {
            endTime = dateFormat.format(orderQuery.getEndTime());
            log.info("endTime: {}", endTime);
        }

        // 先查出与账号id绑定的订单
        IPage<Order> orderIPage = orderMapper.orderList(page, orderQuery.getNumber(), orderQuery.getPhone(), beginTime, endTime, current.getUser().getId());
        List<Order> orders1 = orderIPage.getRecords();

        /**
         * 再通过current获取用户的角色
         * 根据角色获取该用户可以进行审核的订单
         * 0待接单	1待付款	2待发货	3已发货	4已结清	5已取消	6拒单	7待退款	8待收货	9已驳回 10退款完毕
         */
        Integer roleId = current.getRoleId();
        IPage<Order> orderIPageRole = null;
        List<String> status = new ArrayList<>();
        switch (roleId) {
            // 财务人员 -> 获取状态为待退款（7）的订单
            case 2:
                status.add("7");
                orderIPageRole = orderMapper.orderList2(page, orderQuery.getNumber(), orderQuery.getPhone(), beginTime, endTime, status);
                status.clear();
                break;
            // 仓库管理员 -> 待发货 2 | 待收货 8
            case 3:
                status.add("2");
                status.add("8");
                orderIPageRole = orderMapper.orderList2(page, orderQuery.getNumber(), orderQuery.getPhone(), beginTime, endTime, status);
                status.clear();
                break;
            // 客服人员 -> 待接单 0
            case 4:
                status.add("0");
                orderIPageRole = orderMapper.orderList2(page, orderQuery.getNumber(), orderQuery.getPhone(), beginTime, endTime, status);
                status.clear();
                break;
            default:
        }
        List<Order> orders = new ArrayList<>();
        if (ObjectUtil.isNotNull(orderIPageRole)) {
            List<Order> orders2 = orderIPageRole.getRecords();
            orders.addAll(orders2);
        }
        orders.addAll(orders1);
        List<OrderVo> orderVos = new ArrayList<>();
        StringBuilder sb = null;
        for (Order order : orders) {
            OrderVo orderVo = new OrderVo();
            BeanUtil.copyProperties(order, orderVo);
            List<OrderDetail> orderDetails = null;
            // 将订单信息存入redis，从缓存中获取数据，先判断是否存在
            if (!redisTemplate.hasKey("order:detail:" + order.getNumber())) {
                orderDetails = orderDetailService.list(new LambdaQueryWrapper<OrderDetail>().eq(OrderDetail::getOrderId, order.getId()).eq(OrderDetail::getDelFlag, 0));
                String orderDetail = objectMapper.writeValueAsString(orderDetails);
                redisTemplate.opsForValue().set("order:detail:" + order.getNumber(), orderDetail);
            } else {
                log.info("从redis中获取订单详情数据");
                String orderDetailListJson = (String) redisTemplate.opsForValue().get("order:detail:" + order.getNumber());
                //关键：使用 objectMapper 的 readValue(String content, JavaType valueType) 方法
                //注意：此方法返回的是Object，特此这里用了类型强转为List
                orderDetails = (List) objectMapper.readValue(orderDetailListJson, objectMapper.getTypeFactory().constructParametricType(List.class, OrderDetail.class));
            }

            sb = new StringBuilder();
            for (int i = 0; i < orderDetails.size(); i++) {
                if (i == orderDetails.size() - 1) {
                    sb.append(orderDetails.get(i).getFarmProductsName() + "*" + orderDetails.get(i).getNumber());
                    break;
                }
                sb.append(orderDetails.get(i).getFarmProductsName() + "*" + orderDetails.get(i).getNumber() + ",");
            }
            orderVo.setFarmProducts(sb.toString());
            orderVos.add(orderVo);
        }

        return new ListVo<OrderVo>(orderVos, orderIPage.getTotal());
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void saveOrder(SaveOrderQuery saveOrderQuery, LoginUser2 current) {

        List<BaseFarmProducts> farmProductArr = saveOrderQuery.getFarmProductArr();

        // 查询出当前订单农产品的库存
        List<Long> fpIds = new ArrayList<>();
        for (BaseFarmProducts baseFarmProducts : farmProductArr) {
            fpIds.add(baseFarmProducts.getNumber());
        }
        List<JxcInventory> jxcInventories = inventoryService.list(new LambdaQueryWrapper<JxcInventory>().in(JxcInventory::getFarmProductsNumber, fpIds));

        // 判断库存是否充足
        if (ObjectUtil.isNull(jxcInventories) || jxcInventories.size() == 0) {
            throw new AppException(ResponseCode.INSUFFICIENT_INVENTORY);
        }

        Map<Long, Integer> fpMap = new HashMap<>();
        for (JxcInventory jxcInventory : jxcInventories) {
            Long key = jxcInventory.getFarmProductsNumber();
            Integer iyNum = jxcInventory.getIyNum();
            if (fpMap.containsKey(key)){
                Integer count = fpMap.get(key);
                fpMap.put(key, (count + iyNum));
                continue;
            }
            fpMap.put(key, iyNum);
        }

        for (BaseFarmProducts farmProducts : farmProductArr) {
            Long farmProductsNumber = farmProducts.getNumber();
            if (!fpMap.containsKey(farmProductsNumber)) {
                fpMap.put(farmProductsNumber, 0);
            }
        }

        // 判断库存是否充足
        for (int i = 0; i < fpIds.size(); i++) {
            Long number = saveOrderQuery.getFarmProductArr().get(i).getNumber();
            Integer iyNum = fpMap.get(number);
            if (saveOrderQuery.getCountArr().get(i) > iyNum) {
                throw new AppException(ResponseCode.INSUFFICIENT_INVENTORY);
            }
        }

        List<OrderDetail> orderDetails = new ArrayList<>();

        // 计算单个农产品金额 单价*数量
        OrderDetail orderDetail = null;
        for (int i = 0; i < farmProductArr.size(); i++) {
            orderDetail = new OrderDetail();
            BaseFarmProducts farmProducts = farmProductArr.get(i);
            orderDetail.setFarmProductsNumber(farmProducts.getNumber());
            orderDetail.setFarmProductsName(farmProducts.getName());
            orderDetail.setInPrice(farmProducts.getInPrice());
            orderDetail.setPrice(farmProducts.getOutPrice());
            orderDetail.setNumber(saveOrderQuery.getCountArr().get(i));
            orderDetail.setTotalPrice(orderDetail.getPrice().multiply(new BigDecimal(orderDetail.getNumber().toString())));
            orderDetail.setProfit(orderDetail.getTotalPrice().subtract(orderDetail.getInPrice().multiply(new BigDecimal(orderDetail.getNumber().toString()))));
            orderDetail.setDelFlag("0");
            orderDetails.add(orderDetail);
        }

        BigDecimal amount = new BigDecimal(0);
        // 计算总金额
        for (OrderDetail detail : orderDetails) {
            amount = amount.add(detail.getTotalPrice());
        }

        Order order = new Order();
        order.setNumber(snowFlakeUtil.nextId() + "");
        order.setStatus("0");
        order.setUserId(current.getUser().getId());
        order.setOrderTime(new Date());
        order.setPayStatus("0");
        order.setAmount(amount);
        order.setRemarks(saveOrderQuery.getRemarks());
        order.setPhone(saveOrderQuery.getPhone());
        order.setConsignee(saveOrderQuery.getConsignee());
        order.setAddress(saveOrderQuery.getAddress());
        order.setDelFlag("0");

        // 存储订单
        AssertUtil.isTrue(!this.save(order), ResponseCode.ORDER_FAILED);

        // 给订单详情设置订单id
        Order order1 = orderMapper.selectOne(new LambdaQueryWrapper<Order>().eq(Order::getNumber, order.getNumber()));
        OrderDetail orderDetail1 = null;
        for (int i = 0; i < orderDetails.size(); i++) {
            orderDetail1 = orderDetails.get(i);
            orderDetail1.setOrderId(order1.getId());
        }

        // 批量存储订单详情
        AssertUtil.isTrue(!orderDetailService.saveBatch(orderDetails), ResponseCode.ORDER_FAILED);

    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void delById(Long[] ids) {

        /**
         * 入库记录id集合非空 长度不为0
         * 根据id查询出要删除的数据并保存在集合中
         * 批处理删除商品
         */
        AssertUtil.isTrue(null == ids || ids.length == 0, ResponseCode.PLEASE_SELECT_DEL_CONTENT);

        List<Order> orders = new ArrayList<>();
        for (Long id : ids) {
            Order temp = this.getById(id);
            temp.setDelFlag("1");
            orders.add(temp);
            UpdateWrapper<OrderDetail> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("order_id", id).set("del_flag", "1");
            AssertUtil.isTrue(!orderDetailService.update(null,updateWrapper), ResponseCode.DEL_FAILED);
            redisTemplate.delete("order:detail:" + temp.getNumber());
        }
        AssertUtil.isTrue(!this.updateBatchById(orders), ResponseCode.DEL_FAILED);

        LoginUser2 current = (LoginUser2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("用户：{}，进行删除订单操作", current.getUser().getUserName() + "---" + current.getUser().getTrueName());
        Log operateLog = new Log();
        operateLog.setName("删除订单");
        operateLog.setUserName(current.getUser().getUserName() + "---" + current.getUser().getTrueName());
        operateLog.setCreateTime(new Date());
        operateLog.setDelFlag("0");
        AssertUtil.isTrue(!logService.save(operateLog), ResponseCode.DEL_FAILED);

    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void audit(AuditQuery auditQuery, LoginUser2 current) {
        /**
         * 0待接单	1待付款	2待发货	3已发货	4已结清	5已取消	6拒单	7待退款	8待收货	9已驳回 10退款完毕
         */
        switch (auditQuery.getStatus()) {
            case "8":
                AssertUtil.isTrue(!this.update(new UpdateWrapper<Order>().eq("id", auditQuery.getId()).set("status", "7")), ResponseCode.OPERATE_FAILED);
                log.info("用户：{}，进行确认收货操作", current.getUser().getUserName() + "---" + current.getUser().getTrueName());
                Log operateLog2 = new Log();
                operateLog2.setName("确认收货");
                operateLog2.setUserName(current.getUser().getUserName() + "---" + current.getUser().getTrueName());
                operateLog2.setCreateTime(new Date());
                operateLog2.setDelFlag("0");
                AssertUtil.isTrue(!logService.save(operateLog2), ResponseCode.OPERATE_FAILED);
                break;
            case "1":
                AssertUtil.isTrue(!this.update(new UpdateWrapper<Order>().eq("id", auditQuery.getId()).set("status", auditQuery.getStatus())), ResponseCode.OPERATE_FAILED);
                log.info("用户：{}，进行接单操作", current.getUser().getUserName() + "---" + current.getUser().getTrueName());
                Log operateLog3 = new Log();
                operateLog3.setName("接单");
                operateLog3.setUserName(current.getUser().getUserName() + "---" + current.getUser().getTrueName());
                operateLog3.setCreateTime(new Date());
                operateLog3.setDelFlag("0");
                AssertUtil.isTrue(!logService.save(operateLog3), ResponseCode.OPERATE_FAILED);
                break;
            case "6":
                AssertUtil.isTrue(!this.update(new UpdateWrapper<Order>().eq("id", auditQuery.getId()).set("status", auditQuery.getStatus()).set("rejection_reason", auditQuery.getReason())), ResponseCode.OPERATE_FAILED);
                log.info("用户：{}，进行拒单操作", current.getUser().getUserName() + "---" + current.getUser().getTrueName());
                Log operateLog4 = new Log();
                operateLog4.setName("拒单");
                operateLog4.setUserName(current.getUser().getUserName() + "---" + current.getUser().getTrueName());
                operateLog4.setCreateTime(new Date());
                operateLog4.setDelFlag("0");
                AssertUtil.isTrue(!logService.save(operateLog4), ResponseCode.OPERATE_FAILED);
                break;
            case "2":
                AssertUtil.isTrue(!this.update(new UpdateWrapper<Order>().eq("id", auditQuery.getId()).set("status", "3")), ResponseCode.OPERATE_FAILED);
                log.info("用户：{}，进行发货操作", current.getUser().getUserName() + "---" + current.getUser().getTrueName());
                Log operateLog5 = new Log();
                operateLog5.setName("发货");
                operateLog5.setUserName(current.getUser().getUserName() + "---" + current.getUser().getTrueName());
                operateLog5.setCreateTime(new Date());
                operateLog5.setDelFlag("0");
                AssertUtil.isTrue(!logService.save(operateLog5), ResponseCode.OPERATE_FAILED);
                break;
            default:
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void account(AccountQuery accountQuery, LoginUser2 current) throws IOException {
        /**
         * 0待接单	1待付款	2待发货	3已发货	4已结清	5已取消	6拒单	7待退款	8待收货	9已驳回 10退款完毕
         */
        switch (accountQuery.getStatus()) {
            case "9":
                AssertUtil.isTrue(!this.update(new UpdateWrapper<Order>().eq("id", accountQuery.getId()).set("status", accountQuery.getStatus()).set("back_reason", accountQuery.getReason())), ResponseCode.OPERATE_FAILED);
                log.info("用户：{}，进行驳回操作", current.getUser().getUserName() + "---" + current.getUser().getTrueName());
                Log operateLog3 = new Log();
                operateLog3.setName("驳回");
                operateLog3.setUserName(current.getUser().getUserName() + "---" + current.getUser().getTrueName());
                operateLog3.setCreateTime(new Date());
                operateLog3.setDelFlag("0");
                AssertUtil.isTrue(!logService.save(operateLog3), ResponseCode.OPERATE_FAILED);
                break;
            case "10":
                AssertUtil.isTrue(!this.update(new UpdateWrapper<Order>().eq("id", accountQuery.getId()).set("status", accountQuery.getStatus()).set("pay_status", "2")), ResponseCode.OPERATE_FAILED);
                log.info("用户：{}，进行同意退款操作", current.getUser().getUserName() + "---" + current.getUser().getTrueName());
                Log operateLog4 = new Log();
                operateLog4.setName("同意退款");
                operateLog4.setUserName(current.getUser().getUserName() + "---" + current.getUser().getTrueName());
                operateLog4.setCreateTime(new Date());
                operateLog4.setDelFlag("0");
                AssertUtil.isTrue(!logService.save(operateLog4), ResponseCode.OPERATE_FAILED);
                hpWebSocket.sendMessageToAllClient();
                break;
            default:
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void operate(AccountQuery accountQuery, LoginUser2 current) throws IOException {
        switch (accountQuery.getStatus()) {
            case "5":
                AssertUtil.isTrue(!this.update(new UpdateWrapper<Order>().eq("id", accountQuery.getId()).set("status", accountQuery.getStatus()).set("cancel_reason", accountQuery.getReason()).set("cancel_time", new Date())), ResponseCode.OPERATE_FAILED);
                log.info("用户：{}，进行取消订单操作", current.getUser().getUserName() + "---" + current.getUser().getTrueName());
                Log operateLog = new Log();
                operateLog.setName("取消订单");
                operateLog.setUserName(current.getUser().getUserName() + "---" + current.getUser().getTrueName());
                operateLog.setCreateTime(new Date());
                operateLog.setDelFlag("0");
                AssertUtil.isTrue(!logService.save(operateLog), ResponseCode.OPERATE_FAILED);
                break;
            case "3":
                AssertUtil.isTrue(!this.update(new UpdateWrapper<Order>().eq("id", accountQuery.getId()).set("status", "4")), ResponseCode.OPERATE_FAILED);
                log.info("用户：{}，进行确认收货操作", current.getUser().getUserName() + "---" + current.getUser().getTrueName());
                Log operateLog1 = new Log();
                operateLog1.setName("确认收货");
                operateLog1.setUserName(current.getUser().getUserName() + "---" + current.getUser().getTrueName());
                operateLog1.setCreateTime(new Date());
                operateLog1.setDelFlag("0");
                AssertUtil.isTrue(!logService.save(operateLog1), ResponseCode.OPERATE_FAILED);
                break;
            case "2":
                AssertUtil.isTrue(!this.update(new UpdateWrapper<Order>().eq("id", accountQuery.getId()).set("status", accountQuery.getStatus()).set("pay_status", accountQuery.getPayStatus())), ResponseCode.OPERATE_FAILED);
                log.info("用户：{}，进行支付操作", current.getUser().getUserName() + "---" + current.getUser().getTrueName());
                Log operateLog2 = new Log();
                operateLog2.setName("支付");
                operateLog2.setUserName(current.getUser().getUserName() + "---" + current.getUser().getTrueName());
                operateLog2.setCreateTime(new Date());
                operateLog2.setDelFlag("0");
                AssertUtil.isTrue(!logService.save(operateLog2), ResponseCode.OPERATE_FAILED);
                hpWebSocket.sendMessageToAllClient();
                break;
            case "7":
                AssertUtil.isTrue(!this.update(new UpdateWrapper<Order>().eq("id", accountQuery.getId()).set("status", accountQuery.getStatus()).set("refund_reason", accountQuery.getReason())), ResponseCode.OPERATE_FAILED);
                log.info("用户：{}，进行仅退款操作", current.getUser().getUserName() + "---" + current.getUser().getTrueName());
                Log operateLog3 = new Log();
                operateLog3.setName("仅退款");
                operateLog3.setUserName(current.getUser().getUserName() + "---" + current.getUser().getTrueName());
                operateLog3.setCreateTime(new Date());
                operateLog3.setDelFlag("0");
                AssertUtil.isTrue(!logService.save(operateLog3), ResponseCode.OPERATE_FAILED);
                break;
            case "8":
                AssertUtil.isTrue(!this.update(new UpdateWrapper<Order>().eq("id", accountQuery.getId()).set("status", accountQuery.getStatus()).set("refund_reason", accountQuery.getReason())), ResponseCode.OPERATE_FAILED);
                log.info("用户：{}，进行退货退款操作", current.getUser().getUserName() + "---" + current.getUser().getTrueName());
                Log operateLog4 = new Log();
                operateLog4.setName("退货退款");
                operateLog4.setUserName(current.getUser().getUserName() + "---" + current.getUser().getTrueName());
                operateLog4.setCreateTime(new Date());
                operateLog4.setDelFlag("0");
                AssertUtil.isTrue(!logService.save(operateLog4), ResponseCode.OPERATE_FAILED);
                break;
        }
    }
}
