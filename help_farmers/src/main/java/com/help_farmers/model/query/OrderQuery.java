package com.help_farmers.model.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: WZ
 * @Date: 2024/3/25 23:53
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderQuery {

    private String number;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 起始时间
     */
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date beginTime;

    /**
     * 终止时间
     */
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date endTime;

    private Long pageno;

    private Long pagesize;

}
