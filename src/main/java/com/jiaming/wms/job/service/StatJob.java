package com.jiaming.wms.job.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.jiaming.wms.stat.service.IStatGoodsService;
import com.jiaming.wms.stat.service.IStatTradeService;
import com.jiaming.wms.trade.bean.entity.Trade;
import com.jiaming.wms.trade.service.ITradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author dragon
 */
@Slf4j
@Component
public class StatJob {

    @Autowired
    IStatTradeService statTradeService;

    @Autowired
    IStatGoodsService statGoodsService;

    @Autowired
    ITradeService tradeService;

    /**
     * 此定时器是每5分钟执行一次
     * 用于模拟次日凌晨统计前一日的交易数据
     */
    @Async
    @Scheduled(cron="0 */5 * * * ?")
    public void runTrade() {
        Date now = new Date();
        DateTime offsetDay = DateUtil.offsetDay(now, -1);
        String statDate = DateUtil.format(offsetDay, "yyyy-MM-dd");
        log.info("1.从交易表中获取前一日的交易数据 {}", statDate);

        List<Trade> trades = tradeService.getByDate(statDate);
        log.info("2.分析昨日支付成功的交易数据 {}", trades.size());

        log.info("3.计算昨日交易总额、总订单量");
        statTradeService.initData();
        statGoodsService.initData();

        log.info("4.统计分析近30日的交易数据,并放入缓存中");
        statTradeService.statData();

        log.info("5.统计分析近30日的商品数据,并放入缓存中");
        statGoodsService.statData();
    }
}
