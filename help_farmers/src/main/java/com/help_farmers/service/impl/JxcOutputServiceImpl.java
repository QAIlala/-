package com.help_farmers.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.help_farmers.common.exception.AppException;
import com.help_farmers.common.result.ResponseCode;
import com.help_farmers.common.utils.AssertUtil;
import com.help_farmers.mapper.JxcOutputMapper;
import com.help_farmers.model.domain.*;
import com.help_farmers.model.query.StorageAndOutPutQuery;
import com.help_farmers.model.vo.ListVo;
import com.help_farmers.model.vo.OutputVo;
import com.help_farmers.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-24
 */
@Service
@Slf4j
public class JxcOutputServiceImpl extends ServiceImpl<JxcOutputMapper, JxcOutput> implements IJxcOutputService {
    @Resource
    private JxcOutputMapper jxcOutputMapper;

    @Resource
    private IBaseFarmProductsService baseFarmProductsService;

    @Resource
    private IJxcWarehouseService jxcWarehouseService;

    @Resource
    private IJxcInventoryService jxcInventoryService;

    @Resource
    private ILogService logService;

    @Override
    public ListVo<OutputVo> outputList(StorageAndOutPutQuery outputQuery) {

        IPage<OutputVo> page = new Page<>(outputQuery.getPageno(), outputQuery.getPagesize());

        IPage<OutputVo> outputVoIPage = jxcOutputMapper.outputList(page, outputQuery.getFarmProductsNumber(), outputQuery.getFarmProductsName());

        return new ListVo<OutputVo>(outputVoIPage.getRecords(), outputVoIPage.getTotal());

    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void delById(Integer[] oids) {
        /**
         * 出库id集合非空 长度不为0
         * 根据id查询出要删除的数据并保存在集合中
         * 批处理删除商品
         */

        AssertUtil.isTrue(null == oids || oids.length == 0, ResponseCode.PLEASE_SELECT_DEL_CONTENT);

        List<JxcOutput> jxcOutputs = new ArrayList<>();
        for (Integer id : oids) {
            JxcOutput temp = this.getById(id);
            temp.setDelFlag("1");
            jxcOutputs.add(temp);
        }
        AssertUtil.isTrue(!this.updateBatchById(jxcOutputs), ResponseCode.DEL_FAILED);

        LoginUser2 current = (LoginUser2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("用户：{}，进行删除出库记录操作", current.getUser().getUserName() + "---" + current.getUser().getTrueName());
        Log operateLog = new Log();
        operateLog.setName("删除出库记录");
        operateLog.setUserName(current.getUser().getUserName() + "---" + current.getUser().getTrueName());
        operateLog.setCreateTime(new Date());
        operateLog.setDelFlag("0");
        AssertUtil.isTrue(!logService.save(operateLog), ResponseCode.DEL_FAILED);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void saveOutput(JxcOutput jxcOutput, LoginUser2 current) {

        /**
         * 1.农产品编号 非空
         * 仓库id 非空
         * 单位数量单价不为空，联系人，联系电话不为空
         *
         * 2.计算合计价格
         * 将创建人创建时间添加到jxcOutput对象中
         * del_flag = 0
         *
         * 3.出库
         * 通过仓库名查询仓库id
         * 先通过商品id，仓库id，计量单位查询库存是否存在此纪录
         * 如果不存在，返回库存不足
         * 存在：
         * 判断库存数量是否足够
         * 足够 -> 出库成功，并更新库存数量
         * 不足够 -> 库存不足
         */

        // 1.判断参数非空
        AssertUtil.isTrue(null == jxcOutput.getFarmProductsNumber(), ResponseCode.PARM_IS_BLANK);
        AssertUtil.isTrue(null == jxcOutput.getWId(), ResponseCode.PARM_IS_BLANK);
        AssertUtil.isTrue(null == jxcOutput.getONum(), ResponseCode.PARM_IS_BLANK);
        AssertUtil.isTrue(null == jxcOutput.getBatchNum(), ResponseCode.PARM_IS_BLANK);
        AssertUtil.isTrue(null == jxcOutput.getOPeople(), ResponseCode.PARM_IS_BLANK);
        AssertUtil.isTrue(null == jxcOutput.getOPhone(), ResponseCode.PARM_IS_BLANK);

        // 2.处理参数
        BigDecimal oNum = new BigDecimal(jxcOutput.getONum().toString());
        BaseFarmProducts farmProducts = baseFarmProductsService.getOne(new LambdaQueryWrapper<BaseFarmProducts>().eq(BaseFarmProducts::getNumber, jxcOutput.getFarmProductsNumber()).eq(BaseFarmProducts::getDelFlag, 0));
        jxcOutput.setOTotalPrice(farmProducts.getOutPrice().multiply(oNum));
        jxcOutput.setCreateTime(new Date());
        jxcOutput.setCreateBy(current.getUser().getUserName());
        jxcOutput.setDelFlag("0");

        // 3.出库
        JxcWarehouse jxcWarehouse = jxcWarehouseService.getOne(new LambdaQueryWrapper<JxcWarehouse>().eq(JxcWarehouse::getWId, jxcOutput.getWId()).eq(JxcWarehouse::getDelFlag, 0));
        JxcInventory jxcInventory = jxcInventoryService.getOne(new LambdaQueryWrapper<JxcInventory>().eq(JxcInventory::getFarmProductsNumber, jxcOutput.getFarmProductsNumber()).eq(JxcInventory::getWId, jxcWarehouse.getWId()));
        if (ObjectUtil.isNotNull(jxcInventory)) {

            if (jxcOutput.getONum() > jxcInventory.getIyNum()) {
                throw new AppException(ResponseCode.INSUFFICIENT_INVENTORY);
            }

            jxcInventory.setIyNum(jxcInventory.getIyNum() - jxcOutput.getONum());
            AssertUtil.isTrue(!this.save(jxcOutput), ResponseCode.OUTPUT_FAILED);
            AssertUtil.isTrue(!jxcInventoryService.updateById(jxcInventory), ResponseCode.OUTPUT_FAILED);

        } else {
            throw new AppException(ResponseCode.INSUFFICIENT_INVENTORY);
        }
    }
}
