package com.help_farmers.service.impl;


import com.help_farmers.service.IRbacService;
import com.help_farmers.service.IRoleActionService;
import com.help_farmers.service.IRoleMenuService;
import com.help_farmers.service.IUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: WZ
 * @Date: 2024/1/24 13:55
 * @Description:
 */

@Service
public class RbacServiceImpl implements IRbacService {

    @Resource
    private IUserRoleService userRoleService;

    @Resource
    private IRoleMenuService roleMenuService;

    @Resource
    private IRoleActionService roleActionService;

    @Override
    public String findRolesByUserName(String userName) {
        return userRoleService.findRolesByUserName(userName);
    }

    @Override
    public List<String> findAuthoritiesByRoleName(String roleName) {
        return roleMenuService.findAuthoritiesByRoleName(roleName);
    }

    @Override
    public List<String> findActionsByRoleName(String roleName) {
        return roleActionService.findActionsByRoleName(roleName);
    }
}
