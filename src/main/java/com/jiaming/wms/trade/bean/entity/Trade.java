package com.jiaming.wms.trade.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class Trade implements Serializable {
    /**
     * YYYYMMDDHHMMSSSSS+用户手机号后4位，例如：2022010409355512353
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "YYYYMMDDHHMMSSSSS+用户手机号后4位，例如：2022010409355512353")
    private String id;

    /**
     * 0:未支付,1:已支付,2:申请发货,3:已发货,4:已收货
     */
    @ApiModelProperty(value = "0:未支付,1:已支付,2:申请发货,3:已发货,4:已收货")
    private Integer status;

    /**
     * 订单金额
     */
    @ApiModelProperty(value = "订单金额")
    private Long totalPrice;

    /**
     * 1:汇款,2:支付宝,3:微信
     */
    @ApiModelProperty(value = "1:汇款,2:支付宝,3:微信")
    private Integer payType;

    /**
     * 汇款单号
     */
    @ApiModelProperty(value = "汇款单号")
    private String moneyOrder;

    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间")
    private Date payTime;

    /**
     * 发货时间
     */
    @ApiModelProperty(value = "发货时间")
    private Date shipTime;

    /**
     * 收货人姓名
     */
    @ApiModelProperty(value = "收货人姓名")
    private String expressName;

    /**
     * 收货人联系电话
     */
    @ApiModelProperty(value = "收货人联系电话")
    private String expressPhone;

    /**
     * 收货时间
     */
    @ApiModelProperty(value = "收货时间")
    private Date receiveTime;

    /**
     * 收货地所属省份
     */
    @ApiModelProperty(value = "收货地所属省份")
    private Long expressProvId;

    /**
     * 收货地所属城市
     */
    @ApiModelProperty(value = "收货地所属城市")
    private Long expressCityId;

    /**
     * 收货地址
     */
    @ApiModelProperty(value = "收货地址")
    private String expressAddress;

    /**
     * 仓库ID
     */
    @ApiModelProperty(value = "仓库ID")
    private Long storeId;

    /**
     * 仓库员工
     */
    @ApiModelProperty(value = "仓库员工")
    private Long empId;

    /**
     * 客户ID
     */
    @ApiModelProperty(value = "客户ID")
    private Long customerId;

    /**
     * 销售ID
     */
    @ApiModelProperty(value = "销售ID")
    private Long salerId;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private Date updateTime;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}

