package com.jiaming.wms.stat.bean.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class StoreGoodsStat implements Serializable {
    /**
     *
     */
    private Long id;

    /**
     * 入库商品ID
     */
    private Long goodsId;

    /**
     * 入库仓库ID
     */
    private Long storeId;

    /**
     * 商品入库总量
     */
    private Long inTotal;

    /**
     * 商品出库总量
     */
    private Long outTotal;

    /**
     * 商品待出库总量
     */
    private Long outtingTotal;

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

