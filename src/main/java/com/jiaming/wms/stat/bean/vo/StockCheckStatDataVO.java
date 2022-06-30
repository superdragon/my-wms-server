package com.jiaming.wms.stat.bean.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class StockCheckStatDataVO implements Serializable {
    private static final long serialVersionUID = 5943434190167532784L;
    private Long storeId;
    private String storeName;
    private Integer storeStatus;
    private Integer goodsStat;
    // 累计入库总量
    private Long inStockStat;
    // 累计出库总量
    private Long outStockStat;
    // 当前库存总量(包含待出库总量)
    private Long stockStat;
    // 待出库总量
    private Long outtingStockStat;
    // 当前库存总额(包含待出库总量)
    private Long amount;
}
