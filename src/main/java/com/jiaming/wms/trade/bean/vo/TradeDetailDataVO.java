package com.jiaming.wms.trade.bean.vo;

import com.jiaming.wms.trade.bean.entity.Trade;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author dragon
 */
@Data
public class TradeDetailDataVO extends Trade implements Serializable {
    private static final long serialVersionUID = -3925255161257803063L;
    private List<TradeItemDetail> items;
}
