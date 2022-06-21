package com.jiaming.wms.permission.bean.vo;

import com.jiaming.wms.permission.bean.entity.Permission;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author dragon
 */
@Data
public class RolePermissionDataVO implements Serializable {
    private static final long serialVersionUID = 4214251168498173918L;
    private Long id;
    private String name;
    private List<Permission> permissions;
}
