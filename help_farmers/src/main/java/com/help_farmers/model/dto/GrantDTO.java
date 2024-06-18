package com.help_farmers.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: WZ
 * @Date: 2023/6/16 15:25
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrantDTO {

    private Integer roleid;
    private List<Integer> rights;
}
