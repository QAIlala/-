package com.help_farmers.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_base_farm_products")
public class BaseFarmProducts implements Serializable {

    private static final long serialVersionUID = 1L;

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

    /**
     * 是否删除
     */
    private String delFlag;


}
