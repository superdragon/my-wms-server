package com.jiaming.wms.stat.bean.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class StatGoods implements Serializable {
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long id;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long goodsId;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long brandId;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long packId;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long tasteId;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long storeId;

    /**
     * 交易量
     */
    @ApiModelProperty(value = "交易量")
    private Long total;

    /**
     * 交易额
     */
    @ApiModelProperty(value = "交易额")
    private Long amount;

    /**
     * 统计日期
     */
    @ApiModelProperty(value = "统计日期")
    private Integer statDate;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}

