package com.help_farmers.controller.base;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.help_farmers.common.result.ResponseCode;
import com.help_farmers.common.result.Result;
import com.help_farmers.model.domain.BaseFarmProductsType;
import com.help_farmers.model.domain.LoginUser2;
import com.help_farmers.model.query.BaseQuery;
import com.help_farmers.model.query.StringQuery;
import com.help_farmers.service.IBaseFarmProductsTypeService;
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
 * @since 2024-03-22
 */
@RestController
@Slf4j
@RequestMapping("/base-farm-products-type")
public class BaseFarmProductsTypeController {

    @Resource
    private IBaseFarmProductsTypeService baseFarmProductsTypeService;

    /**
     * 获取所有农产品类型（未删除）
     * @return
     */
    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('sys:base-action:farmproductstype:query')")
    public Result getAll() {
        return new Result(ResponseCode.SUCCESS, baseFarmProductsTypeService.list(new LambdaQueryWrapper<BaseFarmProductsType>().select(BaseFarmProductsType::getId, BaseFarmProductsType::getName).eq(BaseFarmProductsType::getDelFlag, 0)));
    }

    /**
     * 获取农产品类型列表
     * @param params
     * @return
     */
    @GetMapping("/typeList")
    @PreAuthorize("hasAuthority('sys:base-action:farmproductstype:query')")
    public Result typeList(String params) throws JsonProcessingException {
        log.info("params: {}", params);
        BaseQuery baseQuery = new ObjectMapper().readValue(params, BaseQuery.class);
        return new Result(ResponseCode.SUCCESS, baseFarmProductsTypeService.typeList(baseQuery));
    }

    /**
     * 更新农产品类型
     * @param params
     * @return
     * @throws JsonProcessingException
     */
    @PatchMapping("/upd")
    @PreAuthorize("hasAuthority('sys:base-action:farmproductstype:upd')")
    public Result updType(@RequestBody StringQuery params) throws JsonProcessingException {
        log.info("baseFarmProductsType: {}", params);
        BaseFarmProductsType baseFarmProductsType = new ObjectMapper().readValue(params.getParams(), BaseFarmProductsType.class);
        LoginUser2 current = (LoginUser2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        baseFarmProductsTypeService.updType(baseFarmProductsType, current);
        return new Result(ResponseCode.SUCCESS, 0);
    }

    /**
     * 删除农产品类型
     * @param typeId
     * @return
     */
    @DeleteMapping("/{typeId}")
    @PreAuthorize("hasAuthority('sys:base-action:farmproductstype:del')")
    public Result delType(@PathVariable Integer typeId) {
        baseFarmProductsTypeService.delType(typeId);
        return new Result(ResponseCode.SUCCESS, 0);
    }


    /**
     * 添加农产品类型
     * @param baseFarmProductsType
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:base-action:farmproductstype:save')")
    public Result saveType(BaseFarmProductsType baseFarmProductsType) {
        LoginUser2 current = (LoginUser2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        baseFarmProductsTypeService.saveType(baseFarmProductsType, current);
        return new Result(ResponseCode.SUCCESS, null);
    }

}
