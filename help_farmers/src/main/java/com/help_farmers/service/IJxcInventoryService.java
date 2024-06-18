package com.help_farmers.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.help_farmers.model.domain.JxcInventory;
import com.help_farmers.model.query.InventoryQuery;
import com.help_farmers.model.vo.InventoryVo;
import com.help_farmers.model.vo.ListVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-23
 */
public interface IJxcInventoryService extends IService<JxcInventory> {

    ListVo<InventoryVo> inventoryList(InventoryQuery inventoryQuery);
}
