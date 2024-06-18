package com.help_farmers.controller.user;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.help_farmers.common.result.ResponseCode;
import com.help_farmers.common.result.Result;
import com.help_farmers.model.domain.LoginUser2;
import com.help_farmers.model.query.UserQuery;
import com.help_farmers.model.vo.LoginUserVo;
import com.help_farmers.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-09
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @Resource(name = "StringRedisTemplate")
    private RedisTemplate redisTemplate;

    @Resource
    private ObjectMapper objectMapper;

    @GetMapping("/logout")
    public Result Logout() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser2 loginUser = (LoginUser2) authentication.getPrincipal();

        redisTemplate.delete("login:" + loginUser.getUser().getId());
        return new Result(ResponseCode.SUCCESS, null);
    }

    /**
     * 修改密码
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @PatchMapping("/updpwd")
    public Result updateUserPassword(String oldPassword, String newPassword) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser2 loginUser = (LoginUser2) authentication.getPrincipal();

        log.info("id: {}, old: {}, new: {}", loginUser, oldPassword, newPassword);
        userService.updateUserPassword(loginUser, oldPassword, newPassword);
        return new Result(ResponseCode.SUCCESS, null);
    }

    /**
     * 获取用户列表（未被删除）
     * @return
     */
    @GetMapping("/userList")
    @PreAuthorize("hasAuthority('sys:sys-set-action:user:query')")
    public Result userList(String params) throws JsonProcessingException {
        log.info("params: {}", params);
        UserQuery userQuery = objectMapper.readValue(params, UserQuery.class);
        return new Result(ResponseCode.SUCCESS, userService.userList(userQuery));
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/delUser/{id}")
    @PreAuthorize("hasAuthority('sys:sys-set-action:user:del')")
    public Result delById(@PathVariable Integer id) {
        userService.delById(id);
        return new Result(ResponseCode.SUCCESS, null);
    }

    @GetMapping("/checkname/{user}")
    public Result checkName(@PathVariable("user") String user) {
        // 1 获得当前登录用户的id
        String[] split = user.split(",");
        String userName = split[0];
        int id = Integer.parseInt(split[1]);

        boolean flag = userService.checkUserName(userName, id);

        return new Result(ResponseCode.SUCCESS, flag);
    }

    @PatchMapping("/resetPass/{userId}")
    @PreAuthorize("hasAuthority('sys:sys-set-action:user:upd')")
    public Result resetPass(@PathVariable Integer userId) {
        userService.resetPass(userId);
        return new Result(ResponseCode.SUCCESS, null);
    }

    @PatchMapping("/updUserByUser")
    public Result updUserByUser(LoginUserVo loginUserVo) {
        userService.updUserByUser(loginUserVo);
        return new Result(ResponseCode.SUCCESS, null);
    }

    @PatchMapping("/updUser")
    @PreAuthorize("hasAuthority('sys:sys-set-action:user:upd')")
    public Result updUser(LoginUserVo loginUserVo) {
        userService.updUser(loginUserVo);
        return new Result(ResponseCode.SUCCESS, null);
    }

    @PostMapping("/saveUser")
    @PreAuthorize("hasAuthority('sys:sys-set-action:user:save')")
    public Result saveUser(LoginUserVo loginUserVo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser2 loginUser = (LoginUser2) authentication.getPrincipal();
        userService.saveUser(loginUserVo, loginUser);
        return new Result(ResponseCode.SUCCESS, 0);
    }


}
