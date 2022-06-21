package com.jiaming.wms.account.bean.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class Account implements Serializable {
    /**
     *
     */
    private Long id;

    /**
     *
     */
    private String loginName;

    /**
     *
     */
    private String userName;

    /**
     *
     */
    private String loginPwd;

    /**
     *
     */
    private String mobile;

    /**
     * 0:可用1:禁用
     */
    private Integer status;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     *
     */
    private Date updateTime;

    /**
     *
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}

