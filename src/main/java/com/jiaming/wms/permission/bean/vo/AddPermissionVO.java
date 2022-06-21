package com.jiaming.wms.permission.bean.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * sys_account_role
 * @author 
 */
@Data
public class AddPermissionVO implements Serializable {
    /**
     * 权限名称
     */
    @NotEmpty(message = "权限名称不能为空")
    private String title;

    @NotEmpty(message = "权限资源别名不能为空")
    private String key;

    private String path;

    /**
     * 0:一级菜单1:二级菜单2:按钮
     */
    @NotNull(message = "菜单等级不能为空")
    private Integer levelId;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单排序序号
     */
    @NotNull(message = "菜单排序号不能为空")
    private Integer orderNo;

    /**
     * 父权限ID
     */
    @NotNull(message = "父级权限ID不能为空")
    private Long pid;
    private static final long serialVersionUID = 1L;
}