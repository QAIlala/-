package com.help_farmers.controller.jxc;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.help_farmers.common.result.ResponseCode;
import com.help_farmers.common.result.Result;
import com.help_farmers.model.domain.JxcOutput;
import com.help_farmers.model.domain.LoginUser2;
import com.help_farmers.model.query.StorageAndOutPutQuery;
import com.help_farmers.model.query.StringQuery;
import com.help_farmers.service.IJxcOutputService;
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
 * @since 2024-03-24
 */
@RestController
@Slf4j
@RequestMapping("/jxc-output")
public class JxcOutputController {

    @Resource
    private IJxcOutputService jxcOutputService;

    @Resource
    private ObjectMapper objectMapper;

    /**
     * 获取出库信息列表
     * @param params
     * @return
     */
    @GetMapping("/outputList")
    @PreAuthorize("hasAuthority('sys:jxc-action:output:query')")
    public Result outputList(String params) throws JsonProcessingException {
        log.info("storageQuery: {}", params);
        StorageAndOutPutQuery outputQuery = objectMapper.readValue(params, StorageAndOutPutQuery.class);
        return new Result(ResponseCode.SUCCESS, jxcOutputService.outputList(outputQuery));
    }

    /**
     * 添加出库信息
     * @param jxcOutput
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:jxc-action:output:save')")
    public Result saveOutput(JxcOutput jxcOutput) {
        LoginUser2 current = (LoginUser2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        jxcOutputService.saveOutput(jxcOutput, current);
        return new Result(ResponseCode.SUCCESS, null);
    }

    /**
     * 删除出库记录
     * @param stringQuery
     * @return
     */
    @DeleteMapping("/del")
    @PreAuthorize("hasAuthority('sys:jxc-action:output:del')")
    public Result delStorage(@RequestBody StringQuery stringQuery) throws JsonProcessingException {
        log.info("stringQuery: {}", stringQuery);
        Integer[] oids = objectMapper.readValue(stringQuery.getParams(), Integer[].class);
        jxcOutputService.delById(oids);
        return new Result(ResponseCode.SUCCESS, null);
    }

}
