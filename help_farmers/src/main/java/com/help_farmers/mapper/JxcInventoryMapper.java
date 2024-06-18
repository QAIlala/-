package com.help_farmers.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.help_farmers.model.domain.JxcInventory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.help_farmers.model.vo.InventoryVo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-23
 */
public interface JxcInventoryMapper extends BaseMapper<JxcInventory> {

    IPage<InventoryVo> inventoryList(IPage<InventoryVo> page, Long farmProductsNumber, String farmProductsName);

}
