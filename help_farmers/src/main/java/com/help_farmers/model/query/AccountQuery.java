package com.help_farmers.model.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: WZ
 * @Date: 2024/3/28 11:04
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountQuery {

    private Long id;

    private String status;

    /**
     * 0未支付	1已支付	2退款
     */
    private String payStatus;

    private String reason;

}
