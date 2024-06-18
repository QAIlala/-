package com.help_farmers.model.query;

import com.help_farmers.model.domain.BaseFarmProducts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: WZ
 * @Date: 2024/3/26 21:32
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveOrderQuery {

    private List<BaseFarmProducts> farmProductArr;

    private List<Integer> countArr;

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
     * 备注信息
     */
    private String remarks;

}
