package com.jiaming.wms.stat.bean.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class StatTrade implements Serializable {
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long id;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long storeId;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long total;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long amount;

    @ApiModelProperty(value = "统计日期")
    private Integer statDate;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private Date crateTime;

    private static final long serialVersionUID = 1L;
}

