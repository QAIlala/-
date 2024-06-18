package com.help_farmers.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: WZ
 * @Date: 2024/3/25 22:43
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVo {

    /**
     * 订单id
     */
    private Long id;

    /**
     * 订单号
     */
    private String number;

    /**
     * 农产品 农产品名称*数量
     */
    private String farmProducts;

    /**
     * 0待接单	1待付款	2待发货	3已发货	4已结清	5已取消	6拒单	7待退款	8待收货	9已驳回 10退款完毕
     */
    private String status;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 下单时间
     */
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date orderTime;

    /**
     * 付款时间
     */
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date payTime;

    /**
     * 0未支付	1已支付	2退款
     */
    private String payStatus;

    /**
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 备注信息
     */
    private String remarks;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 收货人
     */
    private String consignee;

    /**
     * 地址信息
     */
    private String address;

    /**
     * 订单取消原因
     */
    private String cancelReason;

    /**
     * 拒单原因
     */
    private String rejectionReason;

    /**
     * 订单取消时间
     */
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date cancelTime;

    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 驳回原因
     */
    private String backReason;





}
