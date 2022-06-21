package com.jiaming.wms.permission.bean.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author dragon
 */
@Data
public class PermissionDataVO implements Serializable {
    private static final long serialVersionUID = 2822038087387680204L;
    // 菜单集合
    private List<MenuVO> items;
    // 路由集合
    private List<String> paths;
    // 按钮集合
    private Set<String> btns;
}
