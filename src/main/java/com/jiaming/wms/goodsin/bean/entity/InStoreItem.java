package com.jiaming.wms.goodsin.bean.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class InStoreItem implements Serializable {
    /**
     *
     */
    private Long id;

    /**
     *
     */
    private String listId;

    /**
     *
     */
    private Long goodsId;

    /**
     *
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

