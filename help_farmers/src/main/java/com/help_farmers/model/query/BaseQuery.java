package com.help_farmers.model.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 潘鑫
 * @Date: 2024/3/14 19:13
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseQuery {

    private Long pageno;

    private Long pagesize;

}
