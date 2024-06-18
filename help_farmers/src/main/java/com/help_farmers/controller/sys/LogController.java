package com.help_farmers.controller.sys;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.help_farmers.common.result.ResponseCode;
import com.help_farmers.common.result.Result;
import com.help_farmers.model.domain.Log;
import com.help_farmers.model.domain.LoginUser2;
import com.help_farmers.model.query.BaseQuery;
import com.help_farmers.model.query.StringQuery;
import com.help_farmers.model.vo.ListVo;
import com.help_farmers.service.ILogService;
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
 * @since 2024-03-25
 */
@RestController
@Slf4j
@RequestMapping("/log")
public class LogController {

    @Resource
    private ILogService logService;

    @Resource
    private ObjectMapper objectMapper;

    @GetMapping("/logList")
    @PreAuthorize("hasAuthority('sys:sys-authority')")
    public Result logList(String params) throws JsonProcessingException {
        log.info("baseQuery: {}", params);
        BaseQuery baseQuery = objectMapper.readValue(params, BaseQuery.class);
        IPage<Log> page = new Page<>(baseQuery.getPageno(), baseQuery.getPagesize());
        IPage<Log> logIPage = logService.page(page, new LambdaQueryWrapper<Log>().eq(Log::getDelFlag, "0").orderByDesc());
        return new Result(ResponseCode.SUCCESS, new ListVo<>(logIPage.getRecords(), logIPage.getTotal()));
    }

    /**
     * 删除日志记录
     * @param stringQuery
     * @return
     */
    @DeleteMapping("/del")
    @PreAuthorize("hasAuthority('sys:sys-authority')")
    public Result delStorage(@RequestBody StringQuery stringQuery) throws JsonProcessingException {
        log.info("stringQuery: {}", stringQuery);
        Integer[] logIds = objectMapper.readValue(stringQuery.getParams(), Integer[].class);
        LoginUser2 current = (LoginUser2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logService.delById(logIds, current);
        return new Result(ResponseCode.SUCCESS, null);
    }

}
