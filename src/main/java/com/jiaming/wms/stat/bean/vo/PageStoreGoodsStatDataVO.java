package com.jiaming.wms.stat.bean.vo;

import com.jiaming.wms.stat.bean.entity.StoreGoodsStat;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class PageStoreGoodsStatDataVO extends StoreGoodsStat implements Serializable {
    private static final long serialVersionUID = 7975268321679854862L;
    private String storeName;
    private String goodsName;
}
