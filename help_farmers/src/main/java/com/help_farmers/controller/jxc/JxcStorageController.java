package com.help_farmers.controller.jxc;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.help_farmers.common.result.ResponseCode;
import com.help_farmers.common.result.Result;
import com.help_farmers.model.domain.JxcStorage;
import com.help_farmers.model.domain.LoginUser2;
import com.help_farmers.model.query.StorageAndOutPutQuery;
import com.help_farmers.model.query.StringQuery;
import com.help_farmers.service.IJxcStorageService;
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
@RequestMapping("/jxc-storage")
public class JxcStorageController {
    @Resource
    private IJxcStorageService jxcStorageService;

    @Resource
    private ObjectMapper objectMapper;

    /**
     * 获取入库信息列表
     * @param params
     * @return
     */
    @GetMapping("/storageList")
    @PreAuthorize("hasAuthority('sys:jxc-action:storage:query')")
    public Result storageList(String params) throws JsonProcessingException {
        log.info("storageQuery: {}", params);
        StorageAndOutPutQuery storageQuery = objectMapper.readValue(params, StorageAndOutPutQuery.class);
        return new Result(ResponseCode.SUCCESS, jxcStorageService.storageList(storageQuery));
    }

    /**
     * 添加入库信息
     * @param jxcStorage
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:jxc-action:storage:save')")
    public Result saveStorage(JxcStorage jxcStorage) {
        LoginUser2 current = (LoginUser2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        jxcStorageService.saveStorage(jxcStorage, current);
        return new Result(ResponseCode.SUCCESS, null);
    }

    /**
     * 删除入库记录
     * @param stringQuery
     * @return
     */
    @DeleteMapping("/del")
    @PreAuthorize("hasAuthority('sys:jxc-action:storage:del')")
    public Result delStorage(@RequestBody StringQuery stringQuery) throws JsonProcessingException {
        log.info("stringQuery: {}", stringQuery);
        Integer[] sids = objectMapper.readValue(stringQuery.getParams(), Integer[].class);
        jxcStorageService.delById(sids);
        return new Result(ResponseCode.SUCCESS, null);
    }

}
