package com.help_farmers.controller.user;


import com.help_farmers.common.result.ResponseCode;
import com.help_farmers.common.result.Result;
import com.help_farmers.model.domain.LoginUser2;
import com.help_farmers.model.dto.GrantDTO;
import com.help_farmers.service.IMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-12
 */
@Slf4j
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private IMenuService menuService;

    @GetMapping("/queryAllMenus/{roleId}")
    @PreAuthorize("hasAuthority('sys:sys-authority')")
    public Result queryAllMenus(@PathVariable Integer roleId) {
        return menuService.queryAllMenus(roleId);
    }

    @GetMapping("/getMenus")
    public Result getMenus() {
        LoginUser2 current = (LoginUser2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new Result(ResponseCode.SUCCESS, menuService.getMenus(current));
    }

    @PostMapping("/grant")
    @PreAuthorize("hasAuthority('sys:sys-authority')")
    public Result grant(GrantDTO grantDTO) {
        log.info("grantDto: {}", grantDTO);
        menuService.grant(grantDTO);
        return new Result(ResponseCode.SUCCESS, null);
    }

}
