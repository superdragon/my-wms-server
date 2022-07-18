package com.jiaming.wms.saler.bean.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class Saler implements Serializable {
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
    private Date createTime;

    private static final long serialVersionUID = 1L;
}

