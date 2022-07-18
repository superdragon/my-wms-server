package com.jiaming.wms.trade.bean.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class AddTradeItemVO implements Serializable {
    private static final long serialVersionUID = -6893444381215674074L;
    private Long goodsId;
    private Long goodsNum;
    private String goodsName;
}
