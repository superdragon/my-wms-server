package com.jiaming.wms.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaming.wms.permission.bean.entity.Permission;
import com.jiaming.wms.permission.bean.entity.RolePermission;
import com.jiaming.wms.permission.mapper.RolePermissionMapper;
import com.jiaming.wms.permission.service.IRolePermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dragon
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission>
        implements IRolePermissionService {
    @Override
    public List<RolePermission> getByPermissionId(Long id) {
        QueryWrapper<RolePermission> wrapper = Wrappers.query();
        wrapper.eq("permission_id", id);
        return this.list(wrapper);
    }

    @Override
    public List<Permission> getPermissionsByRoleIds(List<Long> roleIds) {
        return this.baseMapper.getPermissionsByRoleIds(roleIds);
    }
}
