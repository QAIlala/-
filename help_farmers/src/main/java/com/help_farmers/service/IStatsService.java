package com.help_farmers.service;

import com.help_farmers.model.dto.OrderNumberDto;
import com.help_farmers.model.vo.StatsVo;

import java.math.BigDecimal;
import java.util.List;

public interface IStatsService {
    StatsVo<Integer> getMonthSales();

    List<OrderNumberDto> getWeekOrderNumber();

    StatsVo<BigDecimal> getWeekProfit();
}
