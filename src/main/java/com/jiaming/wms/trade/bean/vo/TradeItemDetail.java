package com.jiaming.wms.trade.bean.vo;

import com.jiaming.wms.goods.bean.entity.GoodsImages;
import com.jiaming.wms.trade.bean.entity.TradeItem;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author dragon
 */
@Data
public class TradeItemDetail extends TradeItem implements Serializable {
    private static final long serialVersionUID = -486391095656069326L;
    private String brandName;
    private String packName;
    private String tasteName;
    private List<GoodsImages> goodsImages;
}
