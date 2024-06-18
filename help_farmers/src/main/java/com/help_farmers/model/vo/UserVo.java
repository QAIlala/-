package com.help_farmers.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: WZ
 * @Date: 2024/1/30 0:45
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 创建人
     */
    private String createBy;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date createTime;

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


}
