package com.jiaming.wms.trade.bean.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class TradeItem implements Serializable {
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Integer id;

    /**
     * 订单ID
     */
    @ApiModelProperty(value = "订单ID")
    private String tradeId;

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
    private Long goodsId;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long goodsNum;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private Integer goodsPrice;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private String goodsName;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long totalPrice;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private Date updateTime;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}

