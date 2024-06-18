package com.help_farmers.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.help_farmers.common.exception.AppException;
import com.help_farmers.common.result.ResponseCode;
import com.help_farmers.common.utils.AssertUtil;
import com.help_farmers.mapper.JxcStorageMapper;
import com.help_farmers.model.domain.*;
import com.help_farmers.model.query.StorageAndOutPutQuery;
import com.help_farmers.model.vo.ListVo;
import com.help_farmers.model.vo.StorageVo;
import com.help_farmers.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
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
public class JxcStorageServiceImpl extends ServiceImpl<JxcStorageMapper, JxcStorage> implements IJxcStorageService {

    @Resource
    private JxcStorageMapper jxcStorageMapper;

    @Resource
    private IBaseFarmProductsService baseFarmProductsService;

    @Resource
    private IJxcWarehouseService jxcWarehouseService;

    @Resource
    private IJxcInventoryService jxcInventoryService;

    @Resource
    private ILogService logService;

    @Override
    public ListVo<StorageVo> storageList(StorageAndOutPutQuery storageQuery) {

        IPage<StorageVo> page = new Page<>(storageQuery.getPageno(), storageQuery.getPagesize());
        IPage<StorageVo> storageVoIPage = jxcStorageMapper.storageList(page, storageQuery.getFarmProductsNumber(), storageQuery.getFarmProductsName());

        return new ListVo<StorageVo>(storageVoIPage.getRecords(), storageVoIPage.getTotal());
    }

    @Override
    public JxcStorage selectOne(Long farmProductsNumber, Integer wId) {
        return jxcStorageMapper.selectList(new LambdaQueryWrapper<JxcStorage>().eq(JxcStorage::getFarmProductsNumber, farmProductsNumber).eq(JxcStorage::getWId, wId)).get(0);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void saveStorage(JxcStorage jxcStorage, LoginUser2 current) {
        /**
         * 1.农产品编号 非空
         * 仓库id 非空
         * 单位数量单价不为空，联系人，联系电话不为空
         *
         * 2.计算合计价格
         * 将创建人创建时间添加到jxcStorage对象中
         * del_flag = 0
         *
         * 3.入库：
         * 通过仓库名查询仓库id
         * 先通过商品id，仓库id，计量单位查询库存是否存在此纪录
         * 如果存在，增加库存数量（upd）
         * 不存在，新增一条库存
         */

        // 1.判断参数非空
        AssertUtil.isTrue(null == jxcStorage.getFarmProductsNumber(), ResponseCode.PARM_IS_BLANK);
        AssertUtil.isTrue(null == jxcStorage.getWId(), ResponseCode.PARM_IS_BLANK);
        AssertUtil.isTrue(null == jxcStorage.getSNum(), ResponseCode.PARM_IS_BLANK);
        AssertUtil.isTrue(null == jxcStorage.getSPeople(), ResponseCode.PARM_IS_BLANK);
        AssertUtil.isTrue(null == jxcStorage.getSPhone(), ResponseCode.PARM_IS_BLANK);
        AssertUtil.isTrue(null == jxcStorage.getBatchNum(), ResponseCode.PARM_IS_BLANK);

        // 判断仓库是否投入使用
        JxcWarehouse jxcWarehouse = jxcWarehouseService.getOne(new LambdaQueryWrapper<JxcWarehouse>().eq(JxcWarehouse::getWId, jxcStorage.getWId()).eq(JxcWarehouse::getDelFlag, 0));
        if (ObjectUtil.isNull(jxcWarehouse)) {
            throw new AppException(ResponseCode.WAREHOUSE_IS_NOT_BE_USED);
        }

        Date wUsedTime = jxcWarehouse.getWUsedTime();
        LocalDate wUsedTimeLocalDate = wUsedTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();
        if (now.isBefore(wUsedTimeLocalDate)) {
            throw new AppException(ResponseCode.WAREHOUSE_IS_NOT_BE_USED);
        }

        // 2.处理参数
        BigDecimal sNum = new BigDecimal(jxcStorage.getSNum().toString());
        BaseFarmProducts farmProducts = baseFarmProductsService.getOne(new LambdaQueryWrapper<BaseFarmProducts>().eq(BaseFarmProducts::getNumber, jxcStorage.getFarmProductsNumber()).eq(BaseFarmProducts::getDelFlag, 0));
        jxcStorage.setSTotalPrice(farmProducts.getInPrice().multiply(sNum));
        jxcStorage.setCreateTime(new Date());
        jxcStorage.setCreateBy(current.getUser().getUserName());
        jxcStorage.setDelFlag("0");

        // 3.入库

        JxcInventory jxcInventory = jxcInventoryService.getOne(new LambdaQueryWrapper<JxcInventory>().eq(JxcInventory::getFarmProductsNumber, jxcStorage.getFarmProductsNumber()).eq(JxcInventory::getWId, jxcWarehouse.getWId()));
        if (ObjectUtil.isNotNull(jxcInventory)) {
            jxcInventory.setIyNum(jxcInventory.getIyNum() + jxcStorage.getSNum());
            AssertUtil.isTrue(!this.save(jxcStorage), ResponseCode.STORAGE_FAILED);
            AssertUtil.isTrue(!jxcInventoryService.updateById(jxcInventory), ResponseCode.STORAGE_FAILED);
        } else {
            JxcInventory jxcInventoryAdd = new JxcInventory();
            jxcInventoryAdd.setFarmProductsNumber(jxcStorage.getFarmProductsNumber());
            jxcInventoryAdd.setWId(jxcWarehouse.getWId());
            jxcInventoryAdd.setIyNum(jxcStorage.getSNum());
            jxcInventoryAdd.setCreateTime(new Date());
            jxcInventoryAdd.setUnit(farmProducts.getUnit());
            jxcInventoryAdd.setCreateBy(current.getUser().getUserName());
            jxcInventoryAdd.setDelFlag("0");
            AssertUtil.isTrue(!this.save(jxcStorage), ResponseCode.STORAGE_FAILED);
            AssertUtil.isTrue(!jxcInventoryService.save(jxcInventoryAdd), ResponseCode.STORAGE_FAILED);
        }

    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void delById(Integer[] sids) {
        /**
         * 入库记录id集合非空 长度不为0
         * 根据id查询出要删除的数据并保存在集合中
         * 批处理删除
         */

        AssertUtil.isTrue(null == sids || sids.length == 0, ResponseCode.PLEASE_SELECT_DEL_CONTENT);

        List<JxcStorage> jxcStorages = new ArrayList<>();
        for (Integer id : sids) {
            JxcStorage temp = this.getById(id);
            temp.setDelFlag("1");
            jxcStorages.add(temp);
        }
        AssertUtil.isTrue(!this.updateBatchById(jxcStorages), ResponseCode.DEL_FAILED);

        LoginUser2 current = (LoginUser2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("用户：{}，进行删除入库记录操作", current.getUser().getUserName() + "---" + current.getUser().getTrueName());
        Log operateLog = new Log();
        operateLog.setName("删除入库记录");
        operateLog.setUserName(current.getUser().getUserName() + "---" + current.getUser().getTrueName());
        operateLog.setCreateTime(new Date());
        operateLog.setDelFlag("0");
        AssertUtil.isTrue(!logService.save(operateLog), ResponseCode.DEL_FAILED);

    }

}
