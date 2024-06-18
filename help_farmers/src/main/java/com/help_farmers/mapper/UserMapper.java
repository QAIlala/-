package com.help_farmers.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.help_farmers.model.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.help_farmers.model.vo.UserVo;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-09
 */
public interface UserMapper extends BaseMapper<User> {

    IPage<UserVo> userList(IPage<User> page, String userName, String trueName, String phone, Integer roleId);
}
