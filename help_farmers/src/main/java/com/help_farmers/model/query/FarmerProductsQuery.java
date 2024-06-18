package com.help_farmers.model.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: WZ
 * @Date: 2024/3/21 16:07
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmerProductsQuery {

    /**
     * 编号
     */
    private Long number;

    /**
     * 农产品名称
     */
    private String name;

    /**
     * 农产品类型id
     */
    private Integer farmProductsTypeId;

    private Long pageno;

    private Long pagesize;
}
