package com.jiaming.wms.goodsunit.bean.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class GoodsTaste implements Serializable {
    /**
     *
     */
    private Long id;

    /**
     * 商品口味
     */
    private String name;
    private Integer status;

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

