package com.help_farmers.controller.user;


import com.help_farmers.common.result.ResponseCode;
import com.help_farmers.common.result.Result;
import com.help_farmers.model.dto.GrantDTO;
import com.help_farmers.service.IActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-12
 */
@Slf4j
@RestController
@RequestMapping("/action")
public class ActionController {

    @Resource
    private IActionService actionService;

    @GetMapping("/queryAllActions/{roleId}")
    @PreAuthorize("hasAuthority('sys:sys-authority')")
    public Result queryAllActions(@PathVariable Integer roleId) {
        return actionService.queryAllActions(roleId);
    }

    @PostMapping("/grant")
    @PreAuthorize("hasAuthority('sys:sys-authority')")
    public Result grant(GrantDTO grantDTO) {
        log.info("grantDto: {}", grantDTO);
        actionService.grant(grantDTO);
        return new Result(ResponseCode.SUCCESS, null);
    }

}
