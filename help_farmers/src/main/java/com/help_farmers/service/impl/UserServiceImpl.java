package com.help_farmers.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.help_farmers.common.exception.AppException;
import com.help_farmers.common.result.ResponseCode;
import com.help_farmers.common.utils.AssertUtil;
import com.help_farmers.mapper.UserMapper;
import com.help_farmers.model.domain.Log;
import com.help_farmers.model.domain.LoginUser2;
import com.help_farmers.model.domain.User;
import com.help_farmers.model.domain.UserRole;
import com.help_farmers.model.query.UserQuery;
import com.help_farmers.model.vo.ListVo;
import com.help_farmers.model.vo.LoginUserVo;
import com.help_farmers.model.vo.UserVo;
import com.help_farmers.service.ILogService;
import com.help_farmers.service.IUserRoleService;
import com.help_farmers.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-09
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private IUserRoleService userRoleService;

    @Resource
    private ILogService logService;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updateUserPassword(LoginUser2 loginUser, String oldPassword, String newPassword) {

        User user = loginUser.getUser();

        AssertUtil.isTrue(StrUtil.isBlankIfStr(oldPassword), ResponseCode.OLDPASSWORD_NOT_BLANK);
        AssertUtil.isTrue(StrUtil.isBlankIfStr(newPassword), ResponseCode.NEWPASSWORD_NOT_BLANK);

        AssertUtil.isTrue(!passwordEncoder.matches(oldPassword, user.getPassword()), ResponseCode.OLDPASSWORD_INPUT_ERROR);
        AssertUtil.isTrue(newPassword.equals(oldPassword), ResponseCode.NEWPASSWORD_EQUAL_TO_OLDPASSWORD);
        user.setPassword(passwordEncoder.encode(newPassword));
        AssertUtil.isTrue(!this.updateById(user), ResponseCode.PASSWORD_UPDATE_FAILD);

    }

    @Override
    public ListVo<UserVo> userList(UserQuery userQuery) {
        IPage<User> page = new Page<>(userQuery.getPageno(), userQuery.getPagesize());

        IPage<UserVo> userIPage = userMapper.userList(page, userQuery.getUserName(), userQuery.getTrueName(), userQuery.getPhone(), userQuery.getRoleId());

        return new ListVo<UserVo>(userIPage.getRecords(), userIPage.getTotal());
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void delById(Integer id) {

        AssertUtil.isTrue(null == id, ResponseCode.PLEASE_SELECT_DEL_CONTENT);

        int count = userRoleService.count(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, id));

        if (count > 0) {
            AssertUtil.isTrue(!userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, id)), ResponseCode.USER_DEL_ERROR_ASSIGNED);
        }

        User temp = this.getById(id);
        temp.setDelFlag("1");

        AssertUtil.isTrue(!this.updateById(temp), ResponseCode.USER_DEL_ERROR);

        LoginUser2 current = (LoginUser2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("用户：{}，进行删除用户操作", current.getUser().getUserName() + "---" + current.getUser().getTrueName());
        Log operateLog = new Log();
        operateLog.setName("删除用户");
        operateLog.setUserName(current.getUser().getUserName() + "---" + current.getUser().getTrueName());
        operateLog.setCreateTime(new Date());
        operateLog.setDelFlag("0");
        AssertUtil.isTrue(!logService.save(operateLog), ResponseCode.DEL_FAILED);
    }

    /**
     *  userName: 客户端填写的 需要验证的用户名 非空
     *  id： 当前登录用户
     *  返回true 可以注册
     *  返回false 不能注册
     *  SELECT COUNT(id) FROM  `tb_user` WHERE user_name='admin'
     * AND id!=1
     */
    @Override
    public boolean checkUserName(String userName, Integer id) {

        AssertUtil.isTrue(StrUtil.isBlank(userName), ResponseCode.USERNAME_NOTBLANK);
        Integer count = userMapper.selectCount(new LambdaQueryWrapper<User>().eq(User::getUserName, userName).ne(User::getId, id).eq(User::getDelFlag, 0));
        return count == 0;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updUser(LoginUserVo loginUserVo) {

        User user = new User();
        user.setId(loginUserVo.getId());
        user.setUserName(loginUserVo.getUserName());
        user.setTrueName(loginUserVo.getTrueName());
        user.setPhone(loginUserVo.getPhone());
        user.setAddress(loginUserVo.getAddress());
        user.setBanFlag(loginUserVo.getBanFlag());
        user.setRemarks(loginUserVo.getRemarks());

        relationUserRole(loginUserVo.getId(), loginUserVo.getRoleId());

        AssertUtil.isTrue(!this.updateById(user), ResponseCode.USER_UPDATE_FAILD);

    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void saveUser(LoginUserVo loginUserVo, LoginUser2 loginUser) {
        /**
         * 用户名
         * 非空 不可重复
         * 默认密码123456
         * 用户默认有效
         */
        String userName = loginUserVo.getUserName();
        AssertUtil.isTrue(StrUtil.isBlank(userName), ResponseCode.USERNAME_NOTBLANK);

        User user = new User();
        user.setUserName(userName);
        user.setTrueName(loginUserVo.getTrueName());
        user.setPassword(passwordEncoder.encode("a123456"));
        user.setPhone(loginUserVo.getPhone());
        user.setAddress(loginUserVo.getAddress());
        user.setCreateBy(loginUser.getUser().getUserName());
        user.setCreateTime(new Date());
        user.setRemarks(loginUserVo.getRemarks());
        user.setBanFlag(loginUserVo.getBanFlag());
        user.setDelFlag("0");

        AssertUtil.isTrue(!this.save(user), ResponseCode.USER_SAVE_ERROR);

        // 重新查询用户记录
        User temp = this.getUserByUserName(user.getUserName());

        /**
         * 给用户分配角色
         */
        if (!Objects.isNull(temp)) {
            relationUserRole(temp.getId(), loginUserVo.getRoleId());
        }
    }

    @Override
    public User getUserByUserName(String userName) {
        return userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserName, userName).eq(User::getBanFlag, 0).eq(User::getDelFlag, 0));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void resetPass(Integer userId) {
        User user = new User();
        user.setId(userId);
        user.setPassword(passwordEncoder.encode("a123456"));
        AssertUtil.isTrue(!this.updateById(user), ResponseCode.PASSWORD_UPDATE_FAILD);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updUserByUser(LoginUserVo loginUserVo) {

        LoginUser2 current = (LoginUser2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (current.getUser().getId() != loginUserVo.getId()) {
            throw new AppException(ResponseCode.USER_UPDATE_FAILD);
        }

        User user = new User();
        user.setId(loginUserVo.getId());
        user.setUserName(loginUserVo.getUserName());
        user.setTrueName(loginUserVo.getTrueName());
        user.setPhone(loginUserVo.getPhone());
        user.setAddress(loginUserVo.getAddress());
        user.setBanFlag(loginUserVo.getBanFlag());
        user.setRemarks(loginUserVo.getRemarks());

        relationUserRole(loginUserVo.getId(), loginUserVo.getRoleId());

        AssertUtil.isTrue(!this.updateById(user), ResponseCode.USER_UPDATE_FAILD);
    }

    private void relationUserRole(Integer userId, Integer roleId) {
        /**
         * t_user_role
         * 首先查询用户原始角色记录
         * 如果存在就删除所有记录重新添加
         * 如果不存在直接添加
         */
        Integer count = userRoleService.count(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId));
        if (count > 0) {
            AssertUtil.isTrue(!userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId)), ResponseCode.USER_DEL_ERROR_ASSIGNED);
        }

        if (roleId != null) {

            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            AssertUtil.isTrue(!userRoleService.save(userRole), ResponseCode.USER_ROLE_ASSIGNED_FAILED);

        }

    }
}
