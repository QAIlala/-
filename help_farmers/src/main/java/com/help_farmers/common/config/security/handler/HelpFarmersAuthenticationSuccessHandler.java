package com.help_farmers.common.config.security.handler;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.help_farmers.common.result.ResponseCode;
import com.help_farmers.common.result.Result;
import com.help_farmers.common.utils.JwtUtil;
import com.help_farmers.model.domain.LoginUser;
import com.help_farmers.model.domain.LoginUser2;
import com.help_farmers.model.vo.LoginUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Author: WZ
 * @Date: 2024/1/17 23:12
 * @Description:
 */
@Component
@Slf4j
public class HelpFarmersAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {


    @Resource(name = "StringRedisTemplate")
    private RedisTemplate redisTemplate;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        // 获取当前认证的用户
        LoginUser principal = (LoginUser)authentication.getPrincipal();
        log.info("LoginUser: {}", objectMapper.writeValueAsString(principal));

        // 通过用户id生成jwt
        String userId = principal.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);

        LoginUser2 user = new LoginUser2();
        user.setUser(principal.getUser());
        user.setRoleName(principal.getRoleName());
        user.setAuthorities(principal.getAuthorities());
        user.setRoleId(principal.getRoleId());

        String loginUser = objectMapper.writeValueAsString(user);

        // 存储用户信息到redis用于认证
        redisTemplate.opsForValue().set("login:"+userId, loginUser, 7 , TimeUnit.DAYS);

        // 返回前端登录用户信息
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(principal.getUser().getId());
        loginUserVo.setUserName(principal.getUsername());
        loginUserVo.setTrueName(principal.getUser().getTrueName());
        loginUserVo.setPhone(principal.getUser().getPhone());
        loginUserVo.setAddress(principal.getUser().getAddress());
        loginUserVo.setRemarks(principal.getUser().getRemarks());
        loginUserVo.setRoleId(principal.getRoleId());
        loginUserVo.setRoleName(principal.getRoleName());
        loginUserVo.setToken(jwt);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSONUtil.toJsonStr(new Result(ResponseCode.SUCCESS, loginUserVo)));
    }
}
