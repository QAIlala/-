package com.help_farmers.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2024-03-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_jxc_storage")
public class JxcStorage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 入库id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 农产品编号
     */
    private Long farmProductsNumber;

    /**
     * 批号
     */
    private String batchNum;

    /**
     * 入库数量
     */
    private Integer sNum;

    /**
     * 合计价格
     */
    private BigDecimal sTotalPrice;

    /**
     * 仓库id
     */
    private Integer wId;

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

    /**
     * 是否删除
     */
    private String delFlag;


}
