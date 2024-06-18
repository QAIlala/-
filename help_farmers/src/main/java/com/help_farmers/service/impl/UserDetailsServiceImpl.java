package com.help_farmers.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.help_farmers.common.exception.AppException;
import com.help_farmers.common.result.ResponseCode;
import com.help_farmers.common.utils.AssertUtil;
import com.help_farmers.mapper.UserMapper;
import com.help_farmers.model.domain.LoginUser;
import com.help_farmers.model.domain.Role;
import com.help_farmers.model.domain.User;
import com.help_farmers.service.IRbacService;
import com.help_farmers.service.IRoleService;
import com.help_farmers.service.websocket.HPWebSocket;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: WZ
 * @Date: 2024/1/26 20:02
 * @Description:
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private IRbacService rbacService;

    @Resource
    private IRoleService roleService;

    @Resource
    private ThreadLocal<ResponseCode> threadLocal;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AssertUtil.isTrue(StrUtil.isBlankIfStr(username), ResponseCode.USERNAME_OR_PASSWORD_NOTBLANK);

        //根据用户名查询用户信息
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName,username).eq(User::getBanFlag, 0).eq(User::getDelFlag, 0);
        User user = userMapper.selectOne(wrapper);
        //如果查询不到数据就通过抛出异常来给出提示
        if(Objects.isNull(user)){
            throw new AppException(ResponseCode.USER_NOT_FOUND);
        }

        Map<Session, String> userMap = HPWebSocket.getUserMap();
        if (userMap.containsValue(username)) {
            threadLocal.set(ResponseCode.ALREADY_ONLINE);
            throw new AppException(ResponseCode.ALREADY_ONLINE);
        }

        //封装成UserDetails对象返回
        LoginUser loginUser = new LoginUser();
        // 根据用户查询权限信息 添加到LoginUser中
        /**
         * 1.查询用户分配的角色
         * 2.根据用户角色查询角色所拥有的菜单权限和动作权限
         */
        String roleName = rbacService.findRolesByUserName(username);
        if (StrUtil.isNotBlank(roleName)) {
            Role role = roleService.getOne(new LambdaQueryWrapper<Role>().eq(Role::getName, roleName));
            loginUser.setRoleId(role.getId());
            loginUser.setRoleName(roleName);
            List<String> authorities = rbacService.findAuthoritiesByRoleName(roleName);
            List<String> actions = rbacService.findActionsByRoleName(roleName);
            roleName = "ROLE_" + roleName;

            authorities.add(roleName);
            authorities.addAll(actions);
            loginUser.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", authorities)));
        }

        loginUser.setUser(user);

        return loginUser;
    }
}
