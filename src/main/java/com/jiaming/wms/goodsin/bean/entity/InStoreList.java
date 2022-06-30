package com.jiaming.wms.goodsin.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class InStoreList implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     *
     */
    private Long storeId;

    /**
     *
     */
    private Long employeeId;

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

