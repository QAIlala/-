package com.help_farmers.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.help_farmers.mapper.OrderMapper;
import com.help_farmers.model.domain.LoginUser2;
import com.help_farmers.model.dto.OrderNumberDto;
import com.help_farmers.model.dto.ProfitDto;
import com.help_farmers.model.dto.SalesDTO;
import com.help_farmers.model.vo.StatsVo;
import com.help_farmers.service.IStatsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: WZ
 * @Date: 2024/3/30 11:16
 * @Description:
 */

@Service
@Slf4j
public class StatsServiceImpl implements IStatsService {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public StatsVo<Integer> getMonthSales() {
        List<SalesDTO> salesDTOS = orderMapper.getMonthSales();

        Map<String, Integer> temp = new HashMap<>();

        for (SalesDTO salesDTO : salesDTOS) {
            String farmProductsName = salesDTO.getFarmProductsName();
            if (temp.containsKey(farmProductsName)) {
                Integer count = temp.get(farmProductsName);
                temp.put(farmProductsName, (count + salesDTO.getNumber()));
                continue;
            }
            temp.put(farmProductsName, salesDTO.getNumber());
        }

        //转换为list 排序
        List<Map.Entry<String, Integer>> tempList = new ArrayList<Map.Entry<String, Integer>>(temp.entrySet());
        Collections.sort(tempList, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        log.info("tempList: {}", tempList);
        List<String> temp1 = new ArrayList<>();
        List<Integer> temp2 = new ArrayList<>();
        int count = 0;
        for (Map.Entry<String, Integer> entry : tempList) {
            if (count > 10) {
                break;
            }
            temp1.add(entry.getKey());
            temp2.add(entry.getValue());
            count ++;
        }
        return new StatsVo<Integer>(temp1, temp2);
    }

    @Override
    public List<OrderNumberDto> getWeekOrderNumber() {
        LoginUser2 current = (LoginUser2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer roleId = current.getRoleId();
        List<Integer> roleList = new ArrayList<>();
        roleList.add(1);
        roleList.add(2);
        roleList.add(3);
        if (!roleList.contains(roleId)) {
            return null;
        }
        List<OrderNumberDto> weekOrderNumber = orderMapper.getWeekOrderNumber();
        List<OrderNumberDto> orderNumberDtoList = new ArrayList<>();
        for (OrderNumberDto numberDto : weekOrderNumber) {
            OrderNumberDto orderNumberDto = new OrderNumberDto();
            switch (numberDto.getName()) {
                case "1.0":
                    orderNumberDto.setName("星期一");
                    orderNumberDto.setValue(numberDto.getValue());
                    orderNumberDtoList.add(orderNumberDto);
                    break;
                case "2.0":
                    orderNumberDto.setName("星期二");
                    orderNumberDto.setValue(numberDto.getValue());
                    orderNumberDtoList.add(orderNumberDto);
                    break;
                case "3.0":
                    orderNumberDto.setName("星期三");
                    orderNumberDto.setValue(numberDto.getValue());
                    orderNumberDtoList.add(orderNumberDto);
                    break;
                case "4.0":
                    orderNumberDto.setName("星期四");
                    orderNumberDto.setValue(numberDto.getValue());
                    orderNumberDtoList.add(orderNumberDto);
                    break;
                case "5.0":
                    orderNumberDto.setName("星期五");
                    orderNumberDto.setValue(numberDto.getValue());
                    orderNumberDtoList.add(orderNumberDto);
                    break;
                case "6.0":
                    orderNumberDto.setName("星期六");
                    orderNumberDto.setValue(numberDto.getValue());
                    orderNumberDtoList.add(orderNumberDto);
                    break;
                case "7.0":
                    orderNumberDto.setName("星期日");
                    orderNumberDto.setValue(numberDto.getValue());
                    orderNumberDtoList.add(orderNumberDto);
                    break;
            }
        }
        log.info("orderNumberDtoList: {}", orderNumberDtoList);
        return orderNumberDtoList;
    }

    @Override
    public StatsVo<BigDecimal> getWeekProfit() {
        LoginUser2 current = (LoginUser2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer roleId = current.getRoleId();
        List<Integer> roleList = new ArrayList<>();
        roleList.add(1);
        roleList.add(2);
        roleList.add(3);
        if (!roleList.contains(roleId)) {
            return null;
        }
        Integer[] ws = {0, 1, 2, 3, 4, 5, 6};
        List<Integer> weekdays = Arrays.stream(ws).collect(Collectors.toList());

        String[] weekStr = {"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
        List<String> week = Arrays.stream(weekStr).collect(Collectors.toList());

        List<BigDecimal> profits = new ArrayList<>();
        for (Integer weekday : weekdays) {
            List<ProfitDto> profit = orderMapper.getWeekProfit(weekday);
            if (ObjectUtil.isNotNull(profit)) {
                BigDecimal sum = profit.stream().map(ProfitDto::getProfit).reduce(BigDecimal.ZERO, BigDecimal::add);
                profits.add(sum);
                continue;
            }
            profits.add(new BigDecimal("0.00"));

        }
        return new StatsVo<BigDecimal>(week, profits);
    }
}
