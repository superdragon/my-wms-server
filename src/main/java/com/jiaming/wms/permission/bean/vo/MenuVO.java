package com.jiaming.wms.permission.bean.vo;

import com.jiaming.wms.permission.bean.entity.Permission;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author dragon
 * 一级菜单
 */
@Data
public class MenuVO extends Permission implements Serializable {
    private static final long serialVersionUID = -2070272273428069789L;
    // 对应的二级菜单
    private List<MenuNodeVO> children;
}
