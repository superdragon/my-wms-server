package com.jiaming.wms.trade.bean.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author dragon
 */
@Data
public class CheckStoreVO implements Serializable {
    private static final long serialVersionUID = -6303074505613003820L;
    private Long storeId;
    private List<CheckStoreItem> items;
}
