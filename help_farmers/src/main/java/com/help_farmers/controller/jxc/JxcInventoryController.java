package com.help_farmers.controller.jxc;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.help_farmers.common.result.ResponseCode;
import com.help_farmers.common.result.Result;
import com.help_farmers.model.domain.JxcInventory;
import com.help_farmers.model.domain.LoginUser2;
import com.help_farmers.model.query.InventoryQuery;
import com.help_farmers.service.IJxcInventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-23
 */
@RestController
@Slf4j
@RequestMapping("/jxc-inventory")
public class JxcInventoryController {
    @Resource
    private IJxcInventoryService jxcInventoryService;

    @Resource
    private ObjectMapper objectMapper;

    @GetMapping("/inventoryList")
    @PreAuthorize("hasAuthority('sys:jxc-action:inventory:query')")
    public Result inventoryList(String params) throws JsonProcessingException {
        log.info("inventoryQuery: {}", params);
        InventoryQuery inventoryQuery = objectMapper.readValue(params, InventoryQuery.class);
        return new Result(ResponseCode.SUCCESS, jxcInventoryService.inventoryList(inventoryQuery));
    }

    @PatchMapping("/upd")
    @PreAuthorize("hasAuthority('sys:jxc-action:inventory:upd')")
    @Transactional(rollbackFor = {Exception.class})
    public Result updInventory(JxcInventory jxcInventory) {
        LoginUser2 current = (LoginUser2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        jxcInventory.setUpdateTime(new Date());
        jxcInventory.setUpdateBy(current.getUser().getUserName());
        jxcInventoryService.updateById(jxcInventory);
        return new Result(ResponseCode.SUCCESS, null);
    }

}
