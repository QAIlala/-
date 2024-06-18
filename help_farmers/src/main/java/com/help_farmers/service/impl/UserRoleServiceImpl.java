package com.help_farmers.service.impl;

import com.help_farmers.model.domain.UserRole;
import com.help_farmers.mapper.UserRoleMapper;
import com.help_farmers.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-10
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public String findRolesByUserName(String userName) {
         return userRoleMapper.findRolesByUserName(userName);
    }
}
