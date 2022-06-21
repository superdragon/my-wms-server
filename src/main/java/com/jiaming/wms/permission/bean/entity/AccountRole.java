package com.jiaming.wms.permission.bean.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_account_role
 * @author 
 */
@Data
public class AccountRole implements Serializable {
    private Long id;

    /**
     * 账户ID
     */
    private Long accountId;

    /**
     * 角色ID
     */
    private Long roleId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}