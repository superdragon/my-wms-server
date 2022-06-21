package com.jiaming.wms.permission.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaming.wms.permission.bean.entity.Permission;
import com.jiaming.wms.permission.bean.entity.RolePermission;

import java.util.List;

/**
 * @author dragon
 */
public interface IRolePermissionService extends IService<RolePermission> {
    List<RolePermission> getByPermissionId(Long id);

    List<Permission> getPermissionsByRoleIds(List<Long> roleIds);
}
