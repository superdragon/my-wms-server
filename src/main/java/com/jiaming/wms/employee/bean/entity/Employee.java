package com.jiaming.wms.employee.bean.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Employee implements Serializable {
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
    private String phone;

    /**
     *
     */
    private Long storeId;

    /**
     *
     */
    private Integer age;

    /**
     *
     */
    private Integer gender;

    /**
     *
     */
    private Integer status;

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

