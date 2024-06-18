package com.help_farmers.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.help_farmers.common.result.Result;
import com.help_farmers.model.domain.LoginUser2;
import com.help_farmers.model.domain.Menu;
import com.help_farmers.model.dto.GrantDTO;
import com.help_farmers.model.vo.MenuVo;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-12
 */
public interface IMenuService extends IService<Menu> {

    Result queryAllMenus(Integer roleId);

    void grant(GrantDTO grantDTO);

    List<MenuVo> getMenus(LoginUser2 current);
}
