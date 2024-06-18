package com.help_farmers.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.help_farmers.model.domain.JxcInventory;
import com.help_farmers.mapper.JxcInventoryMapper;
import com.help_farmers.model.query.InventoryQuery;
import com.help_farmers.model.vo.InventoryVo;
import com.help_farmers.model.vo.ListVo;
import com.help_farmers.service.IJxcInventoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-23
 */
@Service
public class JxcInventoryServiceImpl extends ServiceImpl<JxcInventoryMapper, JxcInventory> implements IJxcInventoryService {

    @Resource
    private JxcInventoryMapper jxcInventoryMapper;

    @Override
    public ListVo<InventoryVo> inventoryList(InventoryQuery inventoryQuery) {
        IPage<InventoryVo> page = new Page<>(inventoryQuery.getPageno(), inventoryQuery.getPagesize());

        IPage<InventoryVo> inventoryVoIPage = jxcInventoryMapper.inventoryList(page, inventoryQuery.getFarmProductsNumber(), inventoryQuery.getFarmProductsName());

        return new ListVo<InventoryVo>(inventoryVoIPage.getRecords(), inventoryVoIPage.getTotal());
    }
}
