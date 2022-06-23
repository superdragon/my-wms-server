package com.jiaming.wms.goods.bean.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class GoodsImages implements Serializable {
    /**
     *
     */
    private Long id;

    /**
     *
     */
    private Long goodsId;

    /**
     *
     */
    private String imageUrl;

    /**
     *
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}

