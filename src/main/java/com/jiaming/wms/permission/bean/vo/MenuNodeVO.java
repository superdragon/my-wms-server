package com.jiaming.wms.permission.bean.vo;

import com.jiaming.wms.permission.bean.entity.Permission;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author dragon
 * 二级菜单
 */
@Data
public class MenuNodeVO extends Permission implements Serializable {
    private static final long serialVersionUID = -2070272273428069789L;
    // 对应的三级按钮
    private List<Permission> children;
}
