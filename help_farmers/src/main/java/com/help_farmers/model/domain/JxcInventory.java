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
@TableName("t_jxc_inventory")
public class JxcInventory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 库存id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 农产品编号
     */
    private Long farmProductsNumber;

    /**
     * 仓库id
     */
    private Integer wId;

    /**
     * 库存数量
     */
    private Integer iyNum;

    /**
     * 计量单位
     */
    private String unit;

    /**
     * 预警值
     */
    private Integer warningValue;

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
