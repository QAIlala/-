package com.help_farmers.controller.sys;

import com.help_farmers.common.result.ResponseCode;
import com.help_farmers.common.result.Result;
import com.help_farmers.service.IStatsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 潘鑫
 * @since 2024/3/30
 */
@RestController
@Slf4j
@RequestMapping("/stats")
public class StatsController {

    @Resource
    private IStatsService statsService;

    @GetMapping("/getMonthSales")
    public Result getMonthSales() {
        return new Result(ResponseCode.SUCCESS, statsService.getMonthSales());
    }

    @GetMapping("/getWeekOrderNumber")
    public Result getWeekOrderNumber() {
        return new Result(ResponseCode.SUCCESS, statsService.getWeekOrderNumber());
    }

    @GetMapping("/getWeekProfit")
    public Result getWeekProfit() {
        return new Result(ResponseCode.SUCCESS, statsService.getWeekProfit());
    }

}
