package com.help_farmers.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: WZ
 * @Date: 2024/2/5 1:55
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputVo {

    /**
     * 出库id
     */
    private Long id;

    /**
     * 农产品编号
     */
    private Long farmProductsNumber;

    /**
     * 农产品名称
     */
    private String farmProductsName;

    /**
     * 批号
     */
    private String batchNum;


    /**
     * 仓库名称
     */
    private String wName;

    /**
     * 计量单位
     */
    private String unit;

    /**
     * 出库价格
     */
    private BigDecimal outPrice;


    /**
     * 出库数量
     */
    private Integer oNum;


    /**
     * 合计价格
     */
    private BigDecimal oTotalPrice;

    /**
     * 出库人
     */
    private String oPeople;

    /**
     * 出库人联系方式
     */
    private String oPhone;

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

}
