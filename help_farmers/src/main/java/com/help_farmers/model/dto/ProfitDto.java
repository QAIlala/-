package com.help_farmers.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Author: WZ
 * @Date: 2024/3/30 21:08
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfitDto {

    private String weekday;

    private BigDecimal profit;

}
