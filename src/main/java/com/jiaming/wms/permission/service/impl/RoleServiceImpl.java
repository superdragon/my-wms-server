package com.jiaming.wms.permission.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaming.wms.permission.bean.entity.AccountRole;
import com.jiaming.wms.permission.bean.entity.Role;
import com.jiaming.wms.permission.bean.entity.RolePermission;
import com.jiaming.wms.permission.bean.vo.AddRoleVO;
import com.jiaming.wms.permission.bean.vo.UpdateRoleVO;
import com.jiaming.wms.permission.mapper.RoleMapper;
import com.jiaming.wms.permission.service.IAccountRoleService;
import com.jiaming.wms.permission.service.IRolePermissionService;
import com.jiaming.wms.permission.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author dragon
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
        implements IRoleService {

    @Autowired
    IRolePermissionService rolePermissionService;

    @Autowired
    IAccountRoleService accountRoleService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(AddRoleVO roleVO) {
        // 保存角色
        Role sysRole = new Role();
        sysRole.setName(roleVO.getName());
        this.save(sysRole);
        // 保存角色对应的权限
        String[] ids = roleVO.getPermissionIds().split(",");
        List<RolePermission> data = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            RolePermission sysRolePermission = new RolePermission();
            sysRolePermission.setRoleId(sysRole.getId());
            sysRolePermission.setPermissionId(Long.parseLong(ids[i]));
            data.add(sysRolePermission);
        }
        rolePermissionService.saveBatch(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UpdateRoleVO roleVO) {
        // 更新角色
        Role sysRole = new Role();
        sysRole.setName(roleVO.getName());
        sysRole.setId(roleVO.getId());
        this.updateById(sysRole);
        if (StrUtil.isEmpty(roleVO.getPermissionIds())) {
            return;
        }
        // 删除角色以前的权限信息
        QueryWrapper<RolePermission> wrapper = Wrappers.query();
        wrapper.eq("role_id", roleVO.getId());
        rolePermissionService.remove(wrapper);
        // 保存角色新的权限信息
        String[] ids = roleVO.getPermissionIds().split(",");
        List<RolePermission> data = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            RolePermission sysRolePermission = new RolePermission();
            sysRolePermission.setRoleId(sysRole.getId());
            sysRolePermission.setPermissionId(Long.parseLong(ids[i]));
            data.add(sysRolePermission);
        }
        rolePermissionService.saveBatch(data);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeRoleAndPermission(Long id) {
        // 删除角色
        this.removeById(id);
        // 删除角色关联的权限
        QueryWrapper<RolePermission> wrapper = Wrappers.query();
        wrapper.eq("role_id", id);
        rolePermissionService.remove(wrapper);
    }

    @Override
    public List<Role> getByAccountId(Long accountId) {
        return this.baseMapper.getByAccountId(accountId);
    }

    @Override
    public void saveAccountRoles(Long accountId, String roleIds) {
        // 组装保存账户角色的数据
        String[] ids = roleIds.split(",");
        // 去重ID
        Set<Long> setIds = new HashSet<>();
        for (int i = 0; i < ids.length; i++) {
            setIds.add(Long.parseLong(ids[i]));
        }
        if (setIds.size() > 0) {
            // 清除原来的角色权限关系
            QueryWrapper<AccountRole> wrapper = Wrappers.query();
            wrapper.eq("account_id", accountId);
            accountRoleService.remove(wrapper);
        } else {
            return;
        }
        // 创建一个List，用来保存数据
        List<AccountRole> accountRoles = new ArrayList<>();
        for (Long roleId : setIds) {
            AccountRole sysAccountRole = new AccountRole();
            sysAccountRole.setAccountId(accountId);
            sysAccountRole.setRoleId(roleId);
            accountRoles.add(sysAccountRole);
        }
        accountRoleService.saveBatch(accountRoles);
    }
}
