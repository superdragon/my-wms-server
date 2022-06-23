package com.jiaming.wms.goodsunit.bean.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class GoodsBrand implements Serializable {
    /**
     *
     */
    private Long id;

    /**
     * 品牌名称
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

