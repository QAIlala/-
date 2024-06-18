package com.help_farmers.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.help_farmers.model.domain.Role;
import com.help_farmers.model.query.BaseQuery;
import com.help_farmers.model.query.UpdateRoleQuery;
import com.help_farmers.model.vo.ListVo;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-10
 */
public interface IRoleService extends IService<Role> {

    /**
     * 获得未被删除的角色列表
     * @param baseQuery
     * @return
     */
    ListVo<Role> getAllNotDel(BaseQuery baseQuery);

    void updRole(UpdateRoleQuery updateRoleQuery);

    void delRole(Integer roleId);

    void addRole(UpdateRoleQuery updateRoleQuery);
}
