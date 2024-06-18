package com.help_farmers.service.temp;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.help_farmers.mapper.OrderDetailMapper;
import com.help_farmers.model.domain.OrderDetail;
import com.help_farmers.service.IOrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {

    @Resource
    private OrderDetailMapper orderDetailMapper;

    @Resource(name = "StringRedisTemplate")
    private RedisTemplate redisTemplate;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public List<OrderDetail> detail(String params) throws JsonProcessingException {
        String[] paramArray = params.split("-");
        Long id = Long.parseLong(paramArray[0]);
        String number = paramArray[1];

        List<OrderDetail> orderDetails = null;

        // 从redis中取出数据
        if (redisTemplate.hasKey("order:detail:" + number)) {
            log.info("从redis中获取订单详情数据");
            String orderDetailListJson = (String) redisTemplate.opsForValue().get("order:detail:" + number);

            //关键：使用 objectMapper 的 readValue(String content, JavaType valueType) 方法
            //注意：此方法返回的是Object，特此这里用了类型强转为List
            orderDetails = (List) objectMapper.readValue(orderDetailListJson, objectMapper.getTypeFactory().constructParametricType(List.class, OrderDetail.class));
        } else {
            log.info("从数据库中获取订单详情数据");
            orderDetails = orderDetailMapper.selectList(new LambdaQueryWrapper<OrderDetail>().eq(OrderDetail::getOrderId, id).eq(OrderDetail::getDelFlag, 0));
            if (ObjectUtil.isNotNull(orderDetails)) {
                String value = objectMapper.writeValueAsString(orderDetails);
                redisTemplate.opsForValue().set("order:detail:" + number, value);
            }
        }

        return orderDetails;
    }
}
