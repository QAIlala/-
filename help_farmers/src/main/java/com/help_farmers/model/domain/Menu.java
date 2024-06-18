package com.help_farmers.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 节点类型（1为父节点，0为子节点）
     */
    private String state;

    /**
     * 菜单url
     */
    private String url;

    /**
     * 上级菜单id
     */
    private Integer pId;

    /**
     * 权限码
     */
    private String aclValue;

    /**
     * 菜单层级
     */
    private Integer grade;

    /**
     * 是否禁用
     */
    private String banFlag;

    /**
     * 是否删除
     */
    private String delFlag;


}
