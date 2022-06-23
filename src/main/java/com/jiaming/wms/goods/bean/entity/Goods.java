package com.jiaming.wms.goods.bean.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Goods implements Serializable {
    /**
     *
     */
    private Long id;

    /**
     * 商品编码
     */
    private String code;

    /**
     * 品牌ID
     */
    private Long brandId;

    /**
     * 包装ID
     */
    private Long packId;

    /**
     * 口味ID
     */
    private Long tasteId;

    /**
     *
     */
    private String name;

    /**
     * 0:上架,1:下架
     */
    private Integer status;

    /**
     * 销售价格,单位分
     */
    private Integer price;

    /**
     * 成本价格,单位分
     */
    private Integer innerPrice;

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

