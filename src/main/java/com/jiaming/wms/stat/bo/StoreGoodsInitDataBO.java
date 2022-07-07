package com.jiaming.wms.stat.bo;

import com.jiaming.wms.goods.bean.entity.Goods;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class StoreGoodsInitDataBO extends Goods implements Serializable {
    private static final long serialVersionUID = -5882824991143918516L;
    private Long storeId;
}
