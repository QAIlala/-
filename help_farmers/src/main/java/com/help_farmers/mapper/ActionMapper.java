package com.help_farmers.mapper;

import com.help_farmers.model.domain.Action;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.help_farmers.model.dto.TreeDto;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-12
 */
public interface ActionMapper extends BaseMapper<Action> {

    List<TreeDto> queryAllActions();
}
