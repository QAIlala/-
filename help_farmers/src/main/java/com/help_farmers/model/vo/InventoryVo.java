package com.help_farmers.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: WZ
 * @Date: 2024/2/4 23:25
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryVo {

    /**
     * 库存id
     */
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
     * 农产品名称
     */
    private String farmProductsName;

    /**
     * 仓库名称
     */
    private String wName;

    /**
     * 计量单位
     */
    private String unit;

    /**
     * 库存数量
     */
    private Integer iyNum;

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
     * 更新时间
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

}
