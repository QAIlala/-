package com.help_farmers.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.help_farmers.common.result.ResponseCode;
import com.help_farmers.common.result.Result;
import com.help_farmers.common.utils.AssertUtil;
import com.help_farmers.mapper.ActionMapper;
import com.help_farmers.model.domain.Action;
import com.help_farmers.model.domain.Role;
import com.help_farmers.model.domain.RoleAction;
import com.help_farmers.model.dto.GrantDTO;
import com.help_farmers.model.dto.MenuDto;
import com.help_farmers.model.dto.TreeDto;
import com.help_farmers.model.vo.TreeVo;
import com.help_farmers.service.IActionService;
import com.help_farmers.service.IRoleActionService;
import com.help_farmers.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-12
 */
@Service
public class ActionServiceImpl extends ServiceImpl<ActionMapper, Action> implements IActionService {

    @Resource
    private ActionMapper actionMapper;

    @Resource
    private IRoleActionService roleActionService;
    
    @Resource
    private IRoleService roleService;

    @Override
    public Result queryAllActions(Integer roleId) {

        // 查询所有动作权限信息
        List<TreeDto> treeDtos = actionMapper.queryAllActions();
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

        List<Integer> roleHasActionsIds = roleActionService.queryRoleHasActionsByRoleId(roleId);

        return new Result(ResponseCode.SUCCESS, new TreeVo(menuDtos, roleHasActionsIds));
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

        int count = roleActionService.count(new LambdaQueryWrapper<RoleAction>().eq(RoleAction::getRoleId, roleId));
        if (count > 0) {
            AssertUtil.isTrue(!roleActionService.remove(new LambdaQueryWrapper<RoleAction>().eq(RoleAction::getRoleId, roleId)), ResponseCode.ACTIONS_DEL_FAILED);
        }

        List<Integer> rights = grantDTO.getRights();
        if (null != rights && rights.size() > 0) {
            List<RoleAction> roleActions = new ArrayList<>();
            for (Integer mid : rights) {
                RoleAction roleAction = new RoleAction();
                roleAction.setRoleId(roleId);
                roleAction.setActionId(mid);
                roleActions.add(roleAction);
            }

            AssertUtil.isTrue(!roleActionService.saveBatch(roleActions), ResponseCode.ACTIONS_DEL_FAILED_ASSIGNED);
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
