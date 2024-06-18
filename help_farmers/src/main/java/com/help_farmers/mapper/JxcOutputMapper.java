package com.help_farmers.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.help_farmers.model.domain.JxcOutput;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.help_farmers.model.vo.OutputVo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-24
 */
public interface JxcOutputMapper extends BaseMapper<JxcOutput> {

    IPage<OutputVo> outputList(IPage<OutputVo> page, Long farmProductsNumber, String farmProductsName);
}
