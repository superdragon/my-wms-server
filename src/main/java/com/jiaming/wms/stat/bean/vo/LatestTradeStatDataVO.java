package com.jiaming.wms.stat.bean.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author dragon
 */
@Data
public class LatestTradeStatDataVO implements Serializable {
    private static final long serialVersionUID = -4521065757492186400L;
    private Integer sDate;
    private Integer eDate;
    private List<Long> statDates;
    private List<BigDecimal> saleAmount;
    private List<BigDecimal> saleTotal;
}
