package com.jiaming.wms.stat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaming.wms.stat.bean.entity.StatTrade;
import com.jiaming.wms.stat.bean.vo.LatestStoreTradeStatDataVO;
import com.jiaming.wms.stat.bean.vo.LatestTradeStatDataVO;

/**
 * @author dragon
 */
public interface IStatTradeService extends IService<StatTrade> {
    void initData();

    LatestTradeStatDataVO latestStat();

    void statData();

    LatestStoreTradeStatDataVO latestStroeStat();
}
