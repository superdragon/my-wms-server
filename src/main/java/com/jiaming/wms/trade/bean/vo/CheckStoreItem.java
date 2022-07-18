package com.jiaming.wms.trade.bean.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class CheckStoreItem implements Serializable {
    private static final long serialVersionUID = -641482782217122404L;
    private Long goodsId;
    private Long goodsNum;
}
