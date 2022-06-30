package com.jiaming.wms.stat.bean.vo;

import com.jiaming.wms.goods.bean.entity.Goods;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class StoreGoodsStatDataVO extends Goods implements Serializable {
    private static final long serialVersionUID = 220364308825541660L;
    /**
     * 入库仓库ID
     */
    private Long storeId;

    /**
     * 商品入库总量
     */
    private Long inTotal;

    /**
     * 商品出库总量
     */
    private Long outTotal;

    /**
     * 商品待出库总量
     */
    private Long outtingTotal;
}
