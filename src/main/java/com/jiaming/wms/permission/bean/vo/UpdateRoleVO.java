package com.jiaming.wms.permission.bean.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class UpdateRoleVO implements Serializable {
    private static final long serialVersionUID = -948005433520692513L;
    @NotNull(message = "角色ID不能为空")
    private Long id;
    @NotEmpty(message = "角色名称不能为空")
    private String name;
    private String permissionIds;
}
