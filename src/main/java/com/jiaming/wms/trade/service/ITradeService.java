package com.jiaming.wms.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.trade.bean.entity.Trade;
import com.jiaming.wms.trade.bean.vo.*;

import java.util.List;
import java.util.Map;

/**
 * @author dragon
 */
public interface ITradeService extends IService<Trade> {
    void saveTrade(AddTradeVO addTradeVO);

    MyPage<Trade> pageTrade(PageTradeVO pageTradeVO);

    void pay(String id, String moneyOrder);

    void updateStatus(String id, Integer status);

    TradeDetailDataVO detail(String id);

    void goodsOut(GoodsOutVO goodsOutVO);

    Map<Long, CheckStoreDataVO> checkStore(CheckStoreVO checkStoreVO);

    List<Trade> getByDate(String statDate);
}
