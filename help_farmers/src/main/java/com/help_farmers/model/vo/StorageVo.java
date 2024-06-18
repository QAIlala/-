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
public class StorageVo {

    /**
     * 入库id
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
     * 入库价格
     */
    private BigDecimal inPrice;

    /**
     * 入库数量
     */
    private Integer sNum;

    /**
     * 合计价格
     */
    private BigDecimal sTotalPrice;

    /**
     * 入库人
     */
    private String sPeople;

    /**
     * 入库人联系方式
     */
    private String sPhone;

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
