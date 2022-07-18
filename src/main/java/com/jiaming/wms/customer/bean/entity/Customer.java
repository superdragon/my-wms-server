package com.jiaming.wms.customer.bean.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class Customer implements Serializable {
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long id;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private String name;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private String phone;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long salerId;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}

