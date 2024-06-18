package com.help_farmers.controller.order;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.help_farmers.common.result.ResponseCode;
import com.help_farmers.common.result.Result;
import com.help_farmers.service.IOrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-25
 */
@RestController
@Slf4j
@RequestMapping("/order-detail")
public class OrderDetailController {

    @Resource
    private IOrderDetailService orderDetailService;

    @GetMapping("/detail/{params}")
    @PreAuthorize("hasAuthority('sys:sys-order-action:query')")
    public Result detail(@PathVariable String params) throws JsonProcessingException {
        log.info("params: {}", params);
        return new Result(ResponseCode.SUCCESS, orderDetailService.detail(params));
    }

}
