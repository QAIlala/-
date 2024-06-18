package com.help_farmers.service;

import com.help_farmers.model.domain.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-10
 */
public interface IUserRoleService extends IService<UserRole> {

    String findRolesByUserName(String userName);
}
