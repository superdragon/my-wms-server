package com.jiaming.wms.goodsin.bean.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class AddInStoreItemVO implements Serializable {
    private static final long serialVersionUID = 3933724420186418004L;
    private Long goodsId;
    private Long goodsNum;
}
