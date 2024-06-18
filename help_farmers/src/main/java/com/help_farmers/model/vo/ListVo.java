package com.help_farmers.model.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: WZ
 * @Date: 2024/1/20 21:32
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListVo<T> {

    private List<T> data;

    private Long total;
}
