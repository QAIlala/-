package com.help_farmers.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.help_farmers.model.domain.BaseFarmProducts;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.help_farmers.model.vo.FarmProductsVo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-22
 */
public interface BaseFarmProductsMapper extends BaseMapper<BaseFarmProducts> {

    IPage<FarmProductsVo> getFarmProductsList(IPage<FarmProductsVo> page, Long number, String name, Integer farmProductsTypeId);

}
