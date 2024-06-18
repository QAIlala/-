package com.help_farmers.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.help_farmers.common.exception.AppException;
import com.help_farmers.common.result.ResponseCode;
import com.help_farmers.common.utils.AssertUtil;
import com.help_farmers.mapper.JxcWarehouseMapper;
import com.help_farmers.model.domain.JxcInventory;
import com.help_farmers.model.domain.JxcWarehouse;
import com.help_farmers.model.domain.Log;
import com.help_farmers.model.domain.LoginUser2;
import com.help_farmers.model.query.BaseQuery;
import com.help_farmers.model.vo.ListVo;
import com.help_farmers.service.IJxcInventoryService;
import com.help_farmers.service.IJxcWarehouseService;
import com.help_farmers.service.ILogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-23
 */
@Service
@Slf4j
public class JxcWarehouseServiceImpl extends ServiceImpl<JxcWarehouseMapper, JxcWarehouse> implements IJxcWarehouseService {

    @Resource
    private JxcWarehouseMapper jxcWarehouseMapper;

    @Resource
    private ILogService logService;

    @Resource
    private IJxcInventoryService inventoryService;

    @Override
    public ListVo<JxcWarehouse> warehouseList(BaseQuery baseQuery) {

        IPage<JxcWarehouse> page = new Page<>(baseQuery.getPageno(), baseQuery.getPagesize());

        IPage<JxcWarehouse> jxcWarehouseIPage = jxcWarehouseMapper.selectPage(page, new LambdaQueryWrapper<JxcWarehouse>().eq(JxcWarehouse::getDelFlag, 0));

        return new ListVo<JxcWarehouse>(jxcWarehouseIPage.getRecords(), jxcWarehouseIPage.getTotal());
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updWarehouse(JxcWarehouse jxcWarehouse, LoginUser2 current) {
        /**
         * 仓库名称不能为空
         * 添加更新人
         * 添加更新时间
         */
        AssertUtil.isTrue(StrUtil.isBlank(jxcWarehouse.getWName()), ResponseCode.NAME_IS_NOT_NULL);
        jxcWarehouse.setUpdateBy(current.getUser().getUserName());
        jxcWarehouse.setUpdateTime(new Date());
        AssertUtil.isTrue(!this.updateById(jxcWarehouse), ResponseCode.UPDATE_FAILED);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void delWarehouse(Integer wId) {

        /**
         * 先判断仓库中是否还有库存，有的话就不可删除
         */
        List<JxcInventory> inventories = inventoryService.list(new LambdaQueryWrapper<JxcInventory>().eq(JxcInventory::getWId, wId));
        if (ObjectUtil.isNotNull(inventories) &&  inventories.size() != 0) {
            throw new AppException(ResponseCode.WAREHOUSE_HAS_INVENTORY_NOW);
        }

        /**
         * Id 非空
         * 根据Id查询的对象非空
         * is_del = 1
         */
        AssertUtil.isTrue(null == wId, ResponseCode.PLEASE_SELECT_DEL_CONTENT);
        JxcWarehouse jxcWarehouse = jxcWarehouseMapper.selectById(wId);
        AssertUtil.isTrue(null == jxcWarehouse, ResponseCode.DEL_CONTENT_ISNOTEXIST);
        jxcWarehouse.setDelFlag("1");
        AssertUtil.isTrue(!this.updateById(jxcWarehouse), ResponseCode.DEL_FAILED);

        LoginUser2 current = (LoginUser2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("用户：{}，进行删除仓库操作", current.getUser().getUserName() + "---" + current.getUser().getTrueName());
        Log operateLog = new Log();
        operateLog.setName("删除仓库");
        operateLog.setUserName(current.getUser().getUserName() + "---" + current.getUser().getTrueName());
        operateLog.setCreateTime(new Date());
        operateLog.setDelFlag("0");
        AssertUtil.isTrue(!logService.save(operateLog), ResponseCode.DEL_FAILED);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void saveWarehouse(JxcWarehouse jxcWarehouse, LoginUser2 current) {

        /**
         * 仓库名称非空 唯一
         */
        String wName = jxcWarehouse.getWName();
        AssertUtil.isTrue(StrUtil.isBlank(wName), ResponseCode.NAME_IS_NOT_NULL);
        AssertUtil.isTrue(null != jxcWarehouseMapper.selectOne(new LambdaQueryWrapper<JxcWarehouse>().eq(JxcWarehouse::getWName, wName).eq(JxcWarehouse::getDelFlag, 0)), ResponseCode.NAME_IS_EXSIT);
        jxcWarehouse.setCreateTime(new Date());
        jxcWarehouse.setCreateBy(current.getUser().getUserName());
        jxcWarehouse.setDelFlag("0");
        AssertUtil.isTrue(!this.save(jxcWarehouse), ResponseCode.SAVE_FAILED);

    }
}
