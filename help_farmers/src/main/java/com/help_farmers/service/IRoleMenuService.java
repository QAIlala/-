package com.help_farmers.service;

import com.help_farmers.model.domain.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色菜单表 服务类
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-12
 */
public interface IRoleMenuService extends IService<RoleMenu> {

    List<Integer> queryRoleHasMenusByRoleId(Integer roleId);

    List<String> findAuthoritiesByRoleName(String roleName);
}
