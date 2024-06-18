package com.help_farmers.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: WZ
 * @Date: 2024/3/21 16:17
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmProductsVo {

    /**
     * 编号
     */
    private Long number;

    /**
     * 农产品名称
     */
    private String name;

    /**
     * 入库价格
     */
    private BigDecimal inPrice;

    /**
     * 出库价格
     */
    private BigDecimal outPrice;

    /**
     * 单位
     */
    private String unit;


    /**
     * 农产品类型id
     */
    private Integer farmProductsTypeId;

    /**
     * 农产品类型名称
     */
    private String farmProductsTypeName;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 创建时间
     */
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 修改时间
     */
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateBy;

}
