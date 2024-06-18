package com.help_farmers.model.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 潘鑫
 * @Date: 2024/2/2 19:13
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleQuery {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 描述
     */
    private String bz;

    /**
     * 角色名
     */
    private String name;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 是否禁用
     */
    private String banFlag;

    /**
     * 是否删除
     */
    private String delFlag;




}
