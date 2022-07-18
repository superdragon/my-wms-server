package com.jiaming.wms.stat.service;

import com.jiaming.wms.stat.bo.TodayTradeStatBO;
import com.jiaming.wms.trade.bean.vo.TradeDetailDataVO;

/**
 * @author dragon
 */
public interface ITodayStatService {
    void plusTodayTransferTotal();

    Long getByRedis(String key);

    void plusTodayInStoreTotal();

    void pushNotifyPaySuccessInfo(TradeDetailDataVO detail);

    TradeDetailDataVO getLatestNotify();

    void plusTodayTradeTotal(TradeDetailDataVO detail);

    TodayTradeStatBO getTodayTradeStat(String tradeStatkey);

    long getTodayTradeUserStat(String userStatKey);
}
