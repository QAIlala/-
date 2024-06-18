package com.help_farmers.model.domain;

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
 * @since 2024-03-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_action")
public class Action implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 动作权限id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 动作权限名称
     */
    private String name;

    /**
     * 节点类型（1为父节点，0为字节点）
     */
    private String state;

    /**
     * 权限路径
     */
    private String url;

    /**
     * 上级菜单
     */
    private Integer pId;

    /**
     * 权限码
     */
    private String aclValue;

    /**
     * 是否禁用
     */
    private String banFlag;

    /**
     * 是否删除
     */
    private String delFlag;


}
