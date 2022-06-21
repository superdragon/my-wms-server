package com.jiaming.wms.permission.bean.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * sys_permission
 * @author 
 */
@Data
public class UpdatePermissionVO implements Serializable {
    @NotNull(message = "权限ID不能为空")
    private Long id;

    /**
     * 权限名称
     */
    private String title;


    private String key;

    /**
     * 权限资源地址
     */
    private String path;

    /**
     * 0:一级菜单1:二级菜单2:按钮
     */
    private Integer levelId;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单排序序号
     */
    private Integer orderNo;

    /**
     * 父权限ID
     */
    private Long pid;
    private static final long serialVersionUID = 1L;
}