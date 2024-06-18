package com.help_farmers.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: 潘鑫
 * @Date: 2024/3/14 20:11
 * @Description: 一个对象就是菜单上的一个节点
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {

    private Integer id;

    private Integer pId;

    private String name;

    private List<MenuDto> child;
}
