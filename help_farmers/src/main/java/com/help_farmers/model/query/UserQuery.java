package com.help_farmers.model.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: WZ
 * @Date: 2024/1/30 0:26
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQuery {

    private String userName;

    private String trueName;

    private String phone;

    private Integer roleId;

    private Long pageno;

    private Long pagesize;

}
