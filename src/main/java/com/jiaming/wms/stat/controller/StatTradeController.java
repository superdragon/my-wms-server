package com.jiaming.wms.stat.controller;

import com.jiaming.wms.common.response.ResultCodeEnum;
import com.jiaming.wms.common.response.ResultVO;
import com.jiaming.wms.stat.bean.vo.LatestStoreTradeStatDataVO;
import com.jiaming.wms.stat.bean.vo.LatestTradeStatDataVO;
import com.jiaming.wms.stat.service.IStatTradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dragon
 */
@Api(tags = "订单统计API")
@RestController
@RequestMapping("/statTrade")
public class StatTradeController {

    @Autowired
    IStatTradeService statTradeService;

    @ApiOperation("初始化数据(仅测试用)")
    @GetMapping("/initData")
    public String initData() {
        statTradeService.initData();
        return "SUCCESS";
    }

    @ApiOperation("获取近30日交易统计")
    @GetMapping("/latestStat")
    public ResultVO<LatestTradeStatDataVO> latestStat() {
        LatestTradeStatDataVO data = statTradeService.latestStat();
        return new ResultVO<>(ResultCodeEnum.SUCCESS, data);
    }

    @ApiOperation("获取近30日仓储交易统计")
    @GetMapping("/latestStoreStat")
    public ResultVO<LatestStoreTradeStatDataVO> latestStroeStat() {
        LatestStoreTradeStatDataVO data = statTradeService.latestStroeStat();
        return new ResultVO<>(ResultCodeEnum.SUCCESS, data);
    }
}
