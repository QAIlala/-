package com.help_farmers.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_jxc_warehouse")
public class JxcWarehouse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 仓库id
     */
    @TableId(value = "w_id", type = IdType.AUTO)
    private Integer wId;

    /**
     * 仓库编号
     */
    private String wCode;

    /**
     * 仓库名称
     */
    private String wName;

    /**
     * 仓库联系人
     */
    private String wContact;

    /**
     * 仓库联系电话
     */
    private String wPhone;

    /**
     * 仓库地址
     */
    private String wAddress;

    /**
     * 仓库邮编
     */
    private String wPostcode;

    /**
     * 仓库建成时间
     */
    @JsonFormat(
            pattern = "yyyy-MM-dd",
            timezone = "GMT+8"
    )
    private Date wCreatedTime;

    /**
     * 仓库投入使用时间
     */
    @JsonFormat(
            pattern = "yyyy-MM-dd",
            timezone = "GMT+8"
    )
    private Date wUsedTime;

    /**
     * 创建时间
     */
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 修改时间
     */
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 是否删除
     */
    private String delFlag;


}
