package com.help_farmers.model.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_order_detail")
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单详情id
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 订单id
     */
    private Long orderId;

    /**
     * 进货价
     */
    private BigDecimal inPrice;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private Integer number;

    /**
     * 合计价格
     */
    private BigDecimal totalPrice;

    /**
     * 利润
     */
    private BigDecimal profit;

    /**
     * 逻辑删除字段
     */
    private String delFlag;


}
