package com.jiaming.wms.stat.bean.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class TodayStatDataVO implements Serializable {
    private static final long serialVersionUID = -8818495713143899642L;
    private Long transferTotal;
    private Long inStoreTotal;
    private Long tradeAmount;
    private Long tradeTotal;
    private Long userTotal;
}
