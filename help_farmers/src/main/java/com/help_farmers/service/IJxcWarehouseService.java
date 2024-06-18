package com.help_farmers.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.help_farmers.model.domain.JxcWarehouse;
import com.help_farmers.model.domain.LoginUser2;
import com.help_farmers.model.query.BaseQuery;
import com.help_farmers.model.vo.ListVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-23
 */
public interface IJxcWarehouseService extends IService<JxcWarehouse> {

    ListVo<JxcWarehouse> warehouseList(BaseQuery baseQuery);

    void updWarehouse(JxcWarehouse jxcWarehouse, LoginUser2 current);

    void delWarehouse(Integer wId);

    void saveWarehouse(JxcWarehouse jxcWarehouse, LoginUser2 current);

}
