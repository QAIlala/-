package com.help_farmers.mapper;

import com.help_farmers.model.domain.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-10
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    String findRolesByUserName(String userName);
}
