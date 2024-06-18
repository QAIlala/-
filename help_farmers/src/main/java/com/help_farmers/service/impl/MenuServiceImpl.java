package com.help_farmers.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.help_farmers.common.result.ResponseCode;
import com.help_farmers.common.result.Result;
import com.help_farmers.common.utils.AssertUtil;
import com.help_farmers.mapper.MenuMapper;
import com.help_farmers.model.domain.LoginUser2;
import com.help_farmers.model.domain.Menu;
import com.help_farmers.model.domain.Role;
import com.help_farmers.model.domain.RoleMenu;
import com.help_farmers.model.dto.GrantDTO;
import com.help_farmers.model.dto.MenuDto;
import com.help_farmers.model.dto.TreeDto;
import com.help_farmers.model.vo.MenuVo;
import com.help_farmers.model.vo.TreeVo;
import com.help_farmers.service.IMenuService;
import com.help_farmers.service.IRoleMenuService;
import com.help_farmers.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-12
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private IRoleMenuService roleMenuService;

    @Resource
    private IRoleService roleService;

    @Override
    public Result queryAllMenus(Integer roleId) {
        // 查询所有的菜单信息
        List<TreeDto> treeDtos = menuMapper.queryAllMenus();

        List<MenuDto> menuDtos = new ArrayList<>();

        // 获取所有的父级菜单添加到menuDTOS中，获取树级结构的父结点
        treeDtos.stream().forEach(treeDto -> {
            if (treeDto.getPId() == 0) {
                MenuDto menuDto = new MenuDto();
                menuDto.setId(treeDto.getId());
                menuDto.setPId(treeDto.getPId());
                menuDto.setName(treeDto.getName());
                menuDtos.add(menuDto);
            }
        });

        // 通过递归获取所有的子结点
        treeDtos.stream().forEach(treeDto -> {
            getMenuChild(menuDtos, treeDto);
        });

        List<Integer> roleHasMenusIds = roleMenuService.queryRoleHasMenusByRoleId(roleId);

        return new Result(ResponseCode.SUCCESS, new TreeVo(menuDtos, roleHasMenusIds));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void grant(GrantDTO grantDTO) {

        /**
         * 1.参数校验
         *  roleId 非空
         * 2.授权
         *  第一次授权 批量授权
         *  已被授权过
         *  先删除原有权限，再进行批量授权
         */

        Integer roleId = grantDTO.getRoleid();
        Role role = roleService.getById(roleId);
        AssertUtil.isTrue(null == role, ResponseCode.ROLE_ISNOTEXIST);

        int count = roleMenuService.count(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, roleId));
        if (count > 0) {
            AssertUtil.isTrue(!roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, roleId)), ResponseCode.GRANT_DEL_FAILED_ASSIGNED);
        }

        List<Integer> rights = grantDTO.getRights();
        if (null != rights && rights.size() > 0) {
            List<RoleMenu> roleMenus = new ArrayList<>();
            for (Integer mid : rights) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(mid);
                roleMenus.add(roleMenu);
            }

            AssertUtil.isTrue(!roleMenuService.saveBatch(roleMenus), ResponseCode.GRANT_DEL_FAILED);
        }

    }

    @Override
    public List<MenuVo> getMenus(LoginUser2 current) {
        List<MenuVo> menuVos = menuMapper.getMenus(current.getRoleId());
        List<MenuVo> menuVoList = new ArrayList<>();
        // 获取所有的父级菜单添加到menuDTOS中，获取树级结构的父结点
        menuVos.stream().forEach(menuVo -> {
            if (menuVo.getPId() == 0) {
                MenuVo menu = new MenuVo();
                menu.setId(menuVo.getId());
                menu.setPId(menuVo.getPId());
                menu.setName(menuVo.getName());
                menu.setUrl(menuVo.getUrl());
                menu.setState(menuVo.getState());
                menu.setIcon(menuVo.getIcon());
                menuVoList.add(menu);
            }
        });

        // 通过递归获取所有的子结点
        menuVos.stream().forEach(menuVo -> {
            getMenuChilds(menuVoList, menuVo);
        });
        return menuVoList;
    }

    private void getMenuChilds(List<MenuVo> treeList, MenuVo tree) {
        for (MenuVo node : treeList) {
            if(tree.getPId().equals(node.getId())){
                if(node.getChild() == null){
                    node.setChild(new ArrayList<MenuVo>());
                }
                MenuVo menuVo = new MenuVo();
                menuVo.setId(tree.getId());
                menuVo.setPId(tree.getPId());
                menuVo.setName(tree.getName());
                menuVo.setUrl(tree.getUrl());
                menuVo.setState(tree.getState());
                menuVo.setIcon(tree.getIcon());
                node.getChild().add(menuVo);
            }
            if(node.getChild() != null){
                getMenuChilds(node.getChild(),tree);
            }
        }
    }

    private void getMenuChild(List<MenuDto> treeList, TreeDto tree) {
        for (MenuDto node : treeList) {
            if(tree.getPId().equals(node.getId())){
                if(node.getChild() == null){
                    node.setChild(new ArrayList<MenuDto>());
                }
                MenuDto menuDto = new MenuDto();
                menuDto.setId(tree.getId());
                menuDto.setPId(tree.getPId());
                menuDto.setName(tree.getName());
                node.getChild().add(menuDto);
            }
            if(node.getChild() != null){
                getMenuChild(node.getChild(),tree);
            }
        }
    }
}
