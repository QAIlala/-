package com.help_farmers.controller.order;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.help_farmers.common.result.ResponseCode;
import com.help_farmers.common.result.Result;
import com.help_farmers.model.domain.LoginUser2;
import com.help_farmers.model.query.*;
import com.help_farmers.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

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
@RequestMapping("/order")
public class OrderController {

    @Resource
    private IOrderService orderService;

    @Resource
    private ObjectMapper objectMapper;

    @GetMapping("/orderList")
    @PreAuthorize("hasAuthority('sys:sys-order-action:query')")
    public Result orderList(String params) throws JsonProcessingException {
        log.info("orderQuery: {}", params);
        LoginUser2 current = (LoginUser2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OrderQuery orderQuery = objectMapper.readValue(params, OrderQuery.class);
        return new Result(ResponseCode.SUCCESS, orderService.orderList(orderQuery, current));
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:sys-order-action:save')")
    public Result saveOrder(@RequestBody StringQuery orderParams) throws JsonProcessingException {
        log.info("saveOrderQuery: {}", orderParams);
        SaveOrderQuery saveOrderQuery = objectMapper.readValue(orderParams.getParams(), SaveOrderQuery.class);
        log.info("saveOrderQuery: {}", saveOrderQuery);
        LoginUser2 current = (LoginUser2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        orderService.saveOrder(saveOrderQuery, current);
        return new Result(ResponseCode.SUCCESS, null);
    }

    /**
     * 删除订单
     * @param stringQuery
     * @return
     */
    @DeleteMapping("/del")
    @PreAuthorize("hasAuthority('sys:sys-order-action:del')")
    public Result delStorage(@RequestBody StringQuery stringQuery) throws JsonProcessingException {
        log.info("stringQuery: {}", stringQuery);
        Long[] ids = objectMapper.readValue(stringQuery.getParams(), Long[].class);
        orderService.delById(ids);
        return new Result(ResponseCode.SUCCESS, null);
    }

    @PatchMapping("/audit")
    @PreAuthorize("hasAuthority('sys:sys-order-action:audit')")
    public Result audit(@RequestBody StringQuery stringQuery) throws JsonProcessingException {
        log.info("auditQuery: {}", stringQuery);
        AuditQuery auditQuery = objectMapper.readValue(stringQuery.getParams(), AuditQuery.class);
        LoginUser2 current = (LoginUser2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        orderService.audit(auditQuery, current);
        return new Result(ResponseCode.SUCCESS, null);
    }

    @PatchMapping("/account")
    @PreAuthorize("hasAuthority('sys:sys-order-action:account')")
    public Result account(@RequestBody StringQuery stringQuery) throws IOException {
        log.info("auditQuery: {}", stringQuery);
        AccountQuery accountQuery = objectMapper.readValue(stringQuery.getParams(), AccountQuery.class);
        LoginUser2 current = (LoginUser2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        orderService.account(accountQuery, current);
        return new Result(ResponseCode.SUCCESS, null);
    }

    @PatchMapping("/operate")
    @PreAuthorize("hasAuthority('sys:sys-order-action:operate')")
    public Result operate(@RequestBody StringQuery stringQuery) throws IOException {
        log.info("auditQuery: {}", stringQuery);
        AccountQuery accountQuery = objectMapper.readValue(stringQuery.getParams(), AccountQuery.class);
        LoginUser2 current = (LoginUser2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        orderService.operate(accountQuery, current);
        return new Result(ResponseCode.SUCCESS, null);
    }

}
