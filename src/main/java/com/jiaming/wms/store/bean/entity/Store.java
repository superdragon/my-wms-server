package com.jiaming.wms.store.bean.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Store implements Serializable {
    /**
     *
     */
    private Long id;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private String conName;

    /**
     *
     */
    private String conPhone;

    /**
     *
     */
    private Long provId;

    /**
     *
     */
    private Long cityId;

    /**
     *
     */
    private String address;

    /**
     *
     */
    private Integer status;

    private Long safeNum;

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

