package com.jiaming.wms.permission.bean.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * sys_role_permission
 * @author 
 */
@Data
public class RolePermission implements Serializable {
    private Long id;

    /**
     * 权限ID
     */
    private Long permissionId;

    /**
     * 角色ID
     */
    private Long roleId;

    private static final long serialVersionUID = 1L;
}