package com.help_farmers.service.impl;

import com.help_farmers.model.domain.RoleAction;
import com.help_farmers.mapper.RoleActionMapper;
import com.help_farmers.service.IRoleActionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-12
 */
@Service
public class RoleActionServiceImpl extends ServiceImpl<RoleActionMapper, RoleAction> implements IRoleActionService {

    @Resource
    private RoleActionMapper roleActionMapper;

    @Override
    public List<Integer> queryRoleHasActionsByRoleId(Integer roleId) {
        return roleActionMapper.queryRoleHasActionsByRoleId(roleId);
    }

    @Override
    public List<String> findActionsByRoleName(String roleName) {
        return roleActionMapper.findActionsByRoleName(roleName);
    }

}
