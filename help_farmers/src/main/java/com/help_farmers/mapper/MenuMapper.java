package com.help_farmers.mapper;

import com.help_farmers.model.domain.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.help_farmers.model.dto.TreeDto;
import com.help_farmers.model.vo.MenuVo;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-12
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<TreeDto> queryAllMenus();

    List<MenuVo> getMenus(Integer roleId);
}
