package com.jiaming.wms.stat.controller;

import com.jiaming.wms.common.response.ResultCodeEnum;
import com.jiaming.wms.common.response.ResultVO;
import com.jiaming.wms.stat.bean.vo.TodayStatDataVO;
import com.jiaming.wms.stat.bo.TodayTradeStatBO;
import com.jiaming.wms.stat.service.ITodayStatService;
import com.jiaming.wms.trade.bean.vo.TradeDetailDataVO;
import com.jiaming.wms.utils.RedisKeyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dragon
 */
@Api(tags = "今日实时统计API")
@RestController
@RequestMapping("/todayStat")
public class TodayStatController {

    @Autowired
    ITodayStatService todayStatService;

    @ApiOperation("获取今日统计")
    @GetMapping("/total")
    public ResultVO<TodayStatDataVO> total() {
        TodayStatDataVO todayStatDataVO = new TodayStatDataVO();

        // 今日调拨总量
        String key = RedisKeyUtil.getTodayTransferStat();
        Long transferTotal = todayStatService.getByRedis(key);

        // 今日入库总量
        String inStoreStatkey = RedisKeyUtil.getTodayInStoreStat();
        Long inStoreTotal = todayStatService.getByRedis(inStoreStatkey);

        // 今日订单总量
        String tradeStatKey = RedisKeyUtil.getTodayTradeStat();
        TodayTradeStatBO tradeStatBO = todayStatService.getTodayTradeStat(tradeStatKey);

        // 今日下单用户
        String userStatKey = RedisKeyUtil.getTodayTradeUserStat();
        long userTotal = todayStatService.getTodayTradeUserStat(userStatKey);

        todayStatDataVO.setTransferTotal(transferTotal);
        todayStatDataVO.setInStoreTotal(inStoreTotal);
        todayStatDataVO.setTradeAmount(tradeStatBO.getAmount());
        todayStatDataVO.setTradeTotal(tradeStatBO.getTotal());
        todayStatDataVO.setUserTotal(userTotal);

        return new ResultVO<>(ResultCodeEnum.SUCCESS, todayStatDataVO);
    }

    @ApiOperation("获取最新通知")
    @GetMapping("/latestNotify")
    public ResultVO<TradeDetailDataVO> latestNotify() {
        TradeDetailDataVO data = todayStatService.getLatestNotify();
        return new ResultVO<>(ResultCodeEnum.SUCCESS, data);
    }
}
