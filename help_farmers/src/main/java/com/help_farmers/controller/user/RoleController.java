package com.help_farmers.controller.user;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.help_farmers.common.result.ResponseCode;
import com.help_farmers.common.result.Result;
import com.help_farmers.model.domain.Role;
import com.help_farmers.model.query.BaseQuery;
import com.help_farmers.model.query.UpdateRoleQuery;
import com.help_farmers.service.IRoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-10
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    /**
     * 获取角色列表(未被禁用，未被删除)
     * @return
     */
    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('sys:sys-set-action:role:query')")
    public Result getAll() {
        return new Result(ResponseCode.SUCCESS, roleService.list(new LambdaQueryWrapper<Role>().eq(Role::getBanFlag, 0).eq(Role::getDelFlag, 0)));
    }

    /**
     * 获取角色列表(未被删除)
     * @return
     */
    @GetMapping("/getAllNotDel")
    @PreAuthorize("hasAuthority('sys:sys-set-action:role:query')")
    public Result getAllNotDel(BaseQuery baseQuery) {
        return new Result(ResponseCode.SUCCESS, roleService.getAllNotDel(baseQuery));
    }

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    @DeleteMapping("/{roleId}")
    @PreAuthorize("hasAuthority('sys:sys-set-action:role:del')")
    public Result delRole(@PathVariable Integer roleId) {
        roleService.delRole(roleId);
        return new Result(ResponseCode.SUCCESS, null);
    }

    /**
     * 更新角色
     * @return
     */
    @PatchMapping("/upd")
    @PreAuthorize("hasAuthority('sys:sys-set-action:role:upd')")
    public Result upd(UpdateRoleQuery updateRoleQuery) {
        roleService.updRole(updateRoleQuery);
        return new Result(ResponseCode.SUCCESS, null);
    }

    /**
     * 添加角色
     * @param updateRoleQuery
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:sys-set-action:role:save')")
    public Result addRole(UpdateRoleQuery updateRoleQuery) {
        roleService.addRole(updateRoleQuery);
        return new Result(ResponseCode.SUCCESS, null);
    }


}
