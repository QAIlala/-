package com.help_farmers.service.impl;

import com.help_farmers.model.domain.RoleMenu;
import com.help_farmers.mapper.RoleMenuMapper;
import com.help_farmers.service.IRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 角色菜单表 服务实现类
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-12
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<Integer> queryRoleHasMenusByRoleId(Integer roleId) {
        return roleMenuMapper.queryRoleHasMenusByRoleId(roleId);
    }

    @Override
    public List<String> findAuthoritiesByRoleName(String roleName) {
        return roleMenuMapper.findAuthoritiesByRoleName(roleName);
    }

}
