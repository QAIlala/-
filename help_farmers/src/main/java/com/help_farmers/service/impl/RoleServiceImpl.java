package com.help_farmers.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.help_farmers.common.exception.AppException;
import com.help_farmers.common.result.ResponseCode;
import com.help_farmers.common.utils.AssertUtil;
import com.help_farmers.mapper.RoleMapper;
import com.help_farmers.model.domain.*;
import com.help_farmers.model.query.BaseQuery;
import com.help_farmers.model.query.UpdateRoleQuery;
import com.help_farmers.model.vo.ListVo;
import com.help_farmers.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-10
 */
@Service
@Slf4j
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private IUserRoleService userRoleService;

    @Resource
    private IRoleMenuService roleMenuService;

    @Resource
    private IRoleActionService roleActionService;

    @Resource
    private ILogService logService;

    @Override
    public ListVo<Role> getAllNotDel(BaseQuery baseQuery) {
        IPage<Role> page = new Page<>(baseQuery.getPageno(), baseQuery.getPagesize());
        IPage<Role> roleIPage = roleMapper.selectPage(page, new LambdaQueryWrapper<Role>().eq(Role::getDelFlag, 0));

        return new ListVo<Role>(roleIPage.getRecords(), roleIPage.getTotal());
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updRole(UpdateRoleQuery updateRoleQuery) {
        String roleName = updateRoleQuery.getName();
        AssertUtil.isTrue(StrUtil.isBlank(roleName), ResponseCode.ROLENAME_ISNOTBLANK);
        Role roleByRoleName = roleMapper.selectOne(new LambdaQueryWrapper<Role>().eq(Role::getBanFlag, 0).eq(Role::getName, roleName));
        AssertUtil.isTrue(null != roleByRoleName && !roleByRoleName.getId().equals(updateRoleQuery.getId()), ResponseCode.ROLENAME_ISEXIST);
        Role role = new Role();
        role.setId(updateRoleQuery.getId());
        role.setName(updateRoleQuery.getName());
        role.setBz(updateRoleQuery.getBz());
        role.setRemarks(updateRoleQuery.getRemarks());
        role.setBanFlag(updateRoleQuery.getBanFlag());
        AssertUtil.isTrue(!this.updateById(role), ResponseCode.ROLE_UPD_ERROR);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void delRole(Integer roleId) {

        /**
         * 判断是否是具有审核权限的角色，如果是就不可删除
         */
        List<Integer> roleList = new ArrayList<>();
        roleList.add(1);
        roleList.add(2);
        roleList.add(3);
        roleList.add(4);
        if (!roleList.contains(roleId)) {
            throw new AppException(ResponseCode.ROLE_CAN_NOT_DEL);
        }
        /**
         * Id 非空
         * 根据Id查询的对象非空
         * 使用count查询用户角色表是否已有角色
         * 使用count1查询角色动作表是否有权限
         * 使用count2查询角色菜单表是否有权限
         * 如果已有就删除
         * delFlag = 1
         */
        AssertUtil.isTrue(null == roleId, ResponseCode.PLEASE_SELECT_DEL_CONTENT);
        Role role = roleMapper.selectById(roleId);
        AssertUtil.isTrue(null == role, ResponseCode.DEL_CONTENT_ISNOTEXIST);
        int count = userRoleService.count(new LambdaQueryWrapper<UserRole>().eq(UserRole::getRoleId, roleId));
        if (count > 0) {
            AssertUtil.isTrue(!userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getRoleId, roleId)), ResponseCode.ROLE_DEL_FAILED_ASSIGNED);
        }

        int count1 = roleActionService.count(new LambdaQueryWrapper<RoleAction>().eq(RoleAction::getRoleId, roleId));
        if (count1 > 0) {
            AssertUtil.isTrue(!roleActionService.remove(new LambdaQueryWrapper<RoleAction>().eq(RoleAction::getRoleId, roleId)), ResponseCode.ACTIONS_DEL_FAILED);
        }

        int count2 = roleMenuService.count(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, roleId));
        if (count2 > 0) {
            AssertUtil.isTrue(!roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, roleId)), ResponseCode.GRANT_DEL_FAILED_ASSIGNED);
        }

        role.setDelFlag("1");
        AssertUtil.isTrue(!this.updateById(role), ResponseCode.ROLE_DEL_FAILED);

        LoginUser2 current = (LoginUser2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("用户：{}，进行删除角色操作", current.getUser().getUserName() + "---" + current.getUser().getTrueName());
        Log operateLog = new Log();
        operateLog.setName("删除角色");
        operateLog.setUserName(current.getUser().getUserName() + "---" + current.getUser().getTrueName());
        operateLog.setCreateTime(new Date());
        operateLog.setDelFlag("0");
        AssertUtil.isTrue(!logService.save(operateLog), ResponseCode.DEL_FAILED);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void addRole(UpdateRoleQuery updateRoleQuery) {
        /**
         * 角色名 非空 唯一
         * 添加角色
         * 添加权限
         */
        String roleName = updateRoleQuery.getName();
        AssertUtil.isTrue(StrUtil.isBlank(roleName), ResponseCode.ROLENAME_ISNOTBLANK);
        AssertUtil.isTrue(null != roleMapper.selectOne(new LambdaQueryWrapper<Role>().eq(Role::getName, roleName).eq(Role::getDelFlag, 0)), ResponseCode.ROLENAME_ISEXIST);
        Role role = new Role();
        role.setName(updateRoleQuery.getName());
        role.setBz(updateRoleQuery.getBz());
        role.setRemarks(updateRoleQuery.getRemarks());
        role.setBanFlag(updateRoleQuery.getBanFlag());
        role.setDelFlag("0");
        AssertUtil.isTrue(!this.save(role), ResponseCode.ROLE_SAVA_ERROR);
    }
}
