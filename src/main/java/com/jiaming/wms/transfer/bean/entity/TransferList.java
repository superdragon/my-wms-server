package com.jiaming.wms.transfer.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TransferList implements Serializable {
    /**
     * YYYYMMDDHHMMSSSSS+发货仓库ID+收货员工ID+收货仓库ID
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 发货仓库ID
     */
    private Long shipStoreId;

    /**
     * 收货仓库ID
     */
    private Long receiveStoreId;

    /**
     * 发货制单人
     */
    private Long shipEmpId;

    /**
     * 发货时间
     */
    private Date shipTime;

    /**
     * 收货时间
     */
    private Date receiveTime;

    /**
     * 0:配货中。1:已发货。2：已收货。3：取消
     */
    private Integer status;

    /**
     *
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}

