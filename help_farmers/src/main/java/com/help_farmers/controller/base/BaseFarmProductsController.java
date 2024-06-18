package com.help_farmers.controller.base;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.help_farmers.common.result.ResponseCode;
import com.help_farmers.common.result.Result;
import com.help_farmers.model.domain.BaseFarmProducts;
import com.help_farmers.model.domain.LoginUser2;
import com.help_farmers.model.query.FarmerProductsQuery;
import com.help_farmers.service.IBaseFarmProductsService;
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
@RequestMapping("/base-farm-products")
public class BaseFarmProductsController {

    @Resource
    private IBaseFarmProductsService baseFarmProductsService;


    /**
     * 获取农产品列表
     * @param params
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping("/farmProductsList")
    @PreAuthorize("hasAuthority('sys:base-action:farmproducts:query')")
    public Result getFarmProductsList(String params) throws JsonProcessingException {
        log.info("params: {}", params);
        FarmerProductsQuery farmerProductsQuery = new ObjectMapper().readValue(params, FarmerProductsQuery.class);
        return new Result(ResponseCode.SUCCESS, baseFarmProductsService.getFarmProductsList(farmerProductsQuery));
    }

    /**
     * 获取所有农产品（未删除）
     * @return
     */
    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('sys:base-action:farmproducts:query')")
    public Result getAll() {
        return new Result(ResponseCode.SUCCESS, baseFarmProductsService.list(new LambdaQueryWrapper<BaseFarmProducts>().select(BaseFarmProducts::getNumber, BaseFarmProducts::getName, BaseFarmProducts::getInPrice, BaseFarmProducts::getOutPrice).eq(BaseFarmProducts::getDelFlag, 0)));
    }

    /**
     * 更新农产品
     * @param baseFarmProducts
     * @return
     */
    @PatchMapping("/upd")
    @PreAuthorize("hasAuthority('sys:base-action:farmproducts:upd')")
    public Result updFarm(BaseFarmProducts baseFarmProducts) {
        LoginUser2 current = (LoginUser2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        baseFarmProductsService.updFarm(baseFarmProducts, current);
        return new Result(ResponseCode.SUCCESS, null);
    }

    /**
     * 添加农产品
     * @param baseFarmProducts
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:base-action:farmproducts:save')")
    public Result saveFarm(BaseFarmProducts baseFarmProducts) {
        LoginUser2 current = (LoginUser2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        baseFarmProductsService.saveFarm(baseFarmProducts, current);
        return new Result(ResponseCode.SUCCESS, null);
    }

    /**
     * 删除农产品
     * @param number
     * @return
     */
    @DeleteMapping("/{number}")
    @PreAuthorize("hasAuthority('sys:base-action:farmproducts:del')")
    public Result delFarm(@PathVariable Long number) {

        baseFarmProductsService.delFarm(number);

        return new Result(ResponseCode.SUCCESS, null);
    }

}
