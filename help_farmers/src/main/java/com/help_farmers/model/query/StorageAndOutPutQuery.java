package com.help_farmers.model.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: WZ
 * @Date: 2024/3/23 20:12
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StorageAndOutPutQuery {

    /**
     * 农产品编号
     */
    private Long farmProductsNumber;

    /**
     * 农产品名称
     */
    private String farmProductsName;

    private Long pageno;

    private Long pagesize;
}
