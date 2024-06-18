package com.help_farmers.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: WZ
 * @Date: 2024/3/12 19:26
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserVo {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 真实姓名
     */
    private String trueName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 是否禁用
     */
    private String banFlag;

    private Integer roleId;

    private String roleName;

    private String token;
}
