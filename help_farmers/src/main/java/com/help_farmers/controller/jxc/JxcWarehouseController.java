package com.help_farmers.controller.jxc;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.help_farmers.common.result.ResponseCode;
import com.help_farmers.common.result.Result;
import com.help_farmers.model.domain.JxcWarehouse;
import com.help_farmers.model.domain.LoginUser2;
import com.help_farmers.model.query.BaseQuery;
import com.help_farmers.model.query.StringQuery;
import com.help_farmers.service.IJxcWarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
@RequestMapping("/jxc-warehouse")
public class JxcWarehouseController {


    @Resource
    private IJxcWarehouseService jxcWarehouseService;

    @Resource
    private ObjectMapper objectMapper;

    /**
     * 获取仓库列表
     * @param params
     * @return
     */
    @GetMapping("/warehouseList")
    @PreAuthorize("hasAuthority('sys:jxc-action:warehouse:query')")
    public Result warehouseList(String params) throws JsonProcessingException {
        log.info("params: {}", params);
        BaseQuery baseQuery = objectMapper.readValue(params, BaseQuery.class);
        return new Result(ResponseCode.SUCCESS, jxcWarehouseService.warehouseList(baseQuery));
    }

    /**
     * 获取所有仓库（未删除）
     * @return
     */
    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('sys:jxc-action:warehouse:query')")
    public Result getAll() {
        return new Result(ResponseCode.SUCCESS, jxcWarehouseService.list(new LambdaQueryWrapper<JxcWarehouse>().select(JxcWarehouse::getWId, JxcWarehouse::getWName).eq(JxcWarehouse::getDelFlag, 0)));
    }

    /**
     * 更新仓库
     * @param warehouseQuery
     * @return
     */
    @PatchMapping("/upd")
    @PreAuthorize("hasAuthority('sys:jxc-action:warehouse:upd')")
    public Result updWarehouse(@RequestBody StringQuery warehouseQuery) throws JsonProcessingException {
        log.info("jxcWarehouse: {}", warehouseQuery);
        JxcWarehouse jxcWarehouse = objectMapper.readValue(warehouseQuery.getParams(), JxcWarehouse.class);
        LoginUser2 current = (LoginUser2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        jxcWarehouseService.updWarehouse(jxcWarehouse, current);
        return new Result(ResponseCode.SUCCESS, null);
    }

    /**
     * 删除仓库
     * @param wId
     * @return
     */
    @DeleteMapping("/{wId}")
    @PreAuthorize("hasAuthority('sys:jxc-action:warehouse:del')")
    public Result delWarehouse(@PathVariable Integer wId) {
        jxcWarehouseService.delWarehouse(wId);
        return new Result(ResponseCode.SUCCESS, null);
    }

    /**
     * 添加仓库
     * @param warehouseQuery
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:jxc-action:warehouse:save')")
    public Result saveWarehouse(@RequestBody StringQuery warehouseQuery) throws JsonProcessingException {
        log.info("q: {}", warehouseQuery);
        JxcWarehouse jxcWarehouse = objectMapper.readValue(warehouseQuery.getParams(), JxcWarehouse.class);
        LoginUser2 current = (LoginUser2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        jxcWarehouseService.saveWarehouse(jxcWarehouse, current);
        return new Result(ResponseCode.SUCCESS, null);
    }

}
