package com.help_farmers.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: WZ
 * @Date: 2024/3/24 21:55
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuVo {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单url
     */
    private String url;

    /**
     * 上级菜单id
     */
    private Integer pId;

    /**
     * 节点类型（1为父节点，0为子节点）
     */
    private String state;

    private List<MenuVo> child;


}
