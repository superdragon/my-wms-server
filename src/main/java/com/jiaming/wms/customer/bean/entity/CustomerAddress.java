package com.jiaming.wms.customer.bean.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class CustomerAddress implements Serializable {
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long id;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long customerId;

    /**
     * 省份ID
     */
    @ApiModelProperty(value = "省份ID")
    private Long provId;

    /**
     * 城市ID
     */
    @ApiModelProperty(value = "城市ID")
    private Long cityId;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private String address;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}

