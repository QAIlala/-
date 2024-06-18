package com.help_farmers.service;

import com.help_farmers.model.domain.BaseFarmProducts;
import com.baomidou.mybatisplus.extension.service.IService;
import com.help_farmers.model.domain.LoginUser2;
import com.help_farmers.model.query.FarmerProductsQuery;
import com.help_farmers.model.vo.FarmProductsVo;
import com.help_farmers.model.vo.ListVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-22
 */
public interface IBaseFarmProductsService extends IService<BaseFarmProducts> {

    ListVo<FarmProductsVo> getFarmProductsList(FarmerProductsQuery farmerProductsQuery);

    void updFarm(BaseFarmProducts baseFarmProducts, LoginUser2 current);

    void saveFarm(BaseFarmProducts baseFarmProducts, LoginUser2 current);

    void delFarm(Long number);
}
