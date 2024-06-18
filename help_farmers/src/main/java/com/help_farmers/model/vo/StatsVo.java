package com.help_farmers.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: WZ
 * @Date: 2024/3/30 11:20
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatsVo<T> {

    List<String> names;

    List<T> data;

}
