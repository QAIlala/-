package com.help_farmers.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.help_farmers.model.domain.Order;
import com.help_farmers.model.dto.OrderNumberDto;
import com.help_farmers.model.dto.ProfitDto;
import com.help_farmers.model.dto.SalesDTO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-25
 */
public interface OrderMapper extends BaseMapper<Order> {

    IPage<Order> orderList(IPage<Order> page, String number, String phone, String beginTime, String endTime, Integer userId);

    IPage<Order> orderList2(IPage<Order> page, String number, String phone, String beginTime, String endTime, List<String> status);

    List<SalesDTO> getMonthSales();

    List<OrderNumberDto> getWeekOrderNumber();


    List<ProfitDto> getWeekProfit(Integer weekday);
}
