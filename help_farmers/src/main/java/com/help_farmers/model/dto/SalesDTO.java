package com.help_farmers.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: WZ
 * @Date: 2024/3/30 11:13
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesDTO {

    /**
     * 农产品名称
     */
    private String farmProductsName;

    /**
     * 数量
     */
    private Integer number;

}
