package com.jiaming.wms.safestore.bean.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SafeStore implements Serializable {
    /**
     *
     */
    private Long id;

    /**
     * 仓库ID
     */
    private Long storeId;

    /**
     * 商品ID
     */
    private Long goodsId;

    /**
     * 安全阈值
     */
    private Long safeNum;

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

