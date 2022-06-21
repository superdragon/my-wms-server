package com.jiaming.wms.permission.bean.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class Permission implements Serializable {
    /**
     * 菜单ID
     */
    @ApiModelProperty(value = "菜单ID")
    private Long id;

    /**
     * 别名
     */
    @ApiModelProperty(value = "别名")
    @TableField("`key`")
    private String key;

    /**
     * 菜单ICON
     */
    @ApiModelProperty(value = "菜单ICON")
    private String icon;

    /**
     * 菜单等级
     */
    @ApiModelProperty(value = "菜单等级")
    private Integer levelId;

    /**
     * 菜单标题
     */
    @ApiModelProperty(value = "菜单标题")
    private String title;

    /**
     * 菜单路径
     */
    @ApiModelProperty(value = "菜单路径")
    private String path;

    /**
     * 父级菜单的ID
     */
    @ApiModelProperty(value = "父级菜单的ID")
    private Long pid;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer orderNo;

    private static final long serialVersionUID = 1L;
}

