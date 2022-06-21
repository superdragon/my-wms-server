package com.jiaming.wms.permission.bean.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class AddRoleVO implements Serializable {
    private static final long serialVersionUID = -948005433520692513L;
    @NotEmpty(message = "角色名称不能为空")
    private String name;
    @NotEmpty(message = "角色权限不能为空")
    private String permissionIds;
}
