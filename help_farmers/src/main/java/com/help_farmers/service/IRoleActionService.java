package com.help_farmers.service;

import com.help_farmers.model.domain.RoleAction;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-12
 */
public interface IRoleActionService extends IService<RoleAction> {

    List<Integer> queryRoleHasActionsByRoleId(Integer roleId);

    List<String> findActionsByRoleName(String roleName);
}
