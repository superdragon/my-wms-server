package com.jiaming.wms.transfer.bean.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TransferItem implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     * 调拨清单ID
     */
    private String listId;

    /**
     * 商品ID
     */
    private Long goodsId;

    /**
     * 发货数量
     */
    private Long goodsNum;

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

