package com.help_farmers.mapper;

import com.help_farmers.model.domain.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色菜单表 Mapper 接口
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-12
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    List<Integer> queryRoleHasMenusByRoleId(Integer roleId);

    List<String> findAuthoritiesByRoleName(String roleName);
}
