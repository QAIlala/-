package com.help_farmers.service;

import com.help_farmers.model.domain.BaseFarmProductsType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.help_farmers.model.domain.LoginUser2;
import com.help_farmers.model.query.BaseQuery;
import com.help_farmers.model.vo.ListVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-22
 */
public interface IBaseFarmProductsTypeService extends IService<BaseFarmProductsType> {

    ListVo<BaseFarmProductsType> typeList(BaseQuery baseQuery);

    void updType(BaseFarmProductsType baseFarmProductsType, LoginUser2 current);

    void delType(Integer typeId);

    void saveType(BaseFarmProductsType baseFarmProductsType, LoginUser2 current);
}
