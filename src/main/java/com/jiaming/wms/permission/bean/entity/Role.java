package com.jiaming.wms.permission.bean.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_role
 * @author 
 */
@Data
public class Role implements Serializable {
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}