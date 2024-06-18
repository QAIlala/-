package com.help_farmers.model.vo;


import com.help_farmers.model.dto.MenuDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: WZ
 * @Date: 2024/2/2 20:41
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeVo {

    private List<MenuDto> list;

    private List<Integer> checkedId;
}
