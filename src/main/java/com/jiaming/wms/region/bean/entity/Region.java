package com.jiaming.wms.region.bean.entity;

import lombok.Data;

@Data
public class Region {
    /**
     *
     */
    private Long id;

    /**
     * 行政区划代码
     */
    private Long code;

    /**
     * 名称
     */
    private String name;

    /**
     * 上级id
     */
    private Long parentId;

    /**
     * level_id
     */
    private String levelId;
}

