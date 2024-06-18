package com.help_farmers.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.help_farmers.model.domain.LoginUser2;
import com.help_farmers.model.domain.User;
import com.help_farmers.model.query.UserQuery;
import com.help_farmers.model.vo.ListVo;
import com.help_farmers.model.vo.LoginUserVo;
import com.help_farmers.model.vo.UserVo;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-09
 */
public interface IUserService extends IService<User> {

    void updateUserPassword(LoginUser2 loginUser, String oldPassword, String newPassword);

    ListVo<UserVo> userList(UserQuery userQuery);

    void delById(Integer id);

    /**
     *  查询输入的用户名 是否在数据库中存在 当前用户除外
     *  返回true： 不存在（可以注册）
     *  false 存在（不能注册）
     *
     * @param userName
     * @param id
     * @return boolean
     */
    boolean checkUserName(String userName, Integer id);

    void updUser(LoginUserVo loginUserVo);

    void saveUser(LoginUserVo loginUserVo, LoginUser2 loginUser);

    User getUserByUserName(String userName);

    void resetPass(Integer userId);

    void updUserByUser(LoginUserVo loginUserVo);
}
