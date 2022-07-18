package com.jiaming.wms.trade.bean.entity;

/**
 * @author dragon
 */
public class TradeStatus {
    public final static int PRE_PAY = 0; // 未支付
    public final static int PAY_SUCCESS = 1; // 已支付
    public final static int PRE_SHIPPED = 2; // 申请发货
    public final static int SHIPPED_SUCCESS = 3; // 已发货
    public final static int RECEIVE_SUCCESS = 4; // 已收货
}
