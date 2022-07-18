package com.jiaming.wms.trade.bean.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class CheckStoreDataVO implements Serializable {
    private static final long serialVersionUID = 5924854195424452056L;
    private Long goodsId;
    private Long goodsNum;
    private Long storeNum;
    private Boolean isOk;
}
