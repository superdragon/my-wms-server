package com.jiaming.wms.trade.controller;

import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.common.response.ResultCodeEnum;
import com.jiaming.wms.common.response.ResultVO;
import com.jiaming.wms.trade.bean.entity.Trade;
import com.jiaming.wms.trade.bean.vo.*;
import com.jiaming.wms.trade.service.ITradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author dragon
 */

@Api(tags = "交易API")
@RestController
@RequestMapping("/trade")
public class TradeController {

    @Autowired
    ITradeService tradeService;

    @ApiOperation("保存订单信息")
    @PostMapping("/add")
    public ResultVO add(@RequestBody @Valid AddTradeVO addTradeVO) {
        tradeService.saveTrade(addTradeVO);
        return new ResultVO(ResultCodeEnum.SUCCESS);
    }

    @ApiOperation("分页查询订单信息")
    @PostMapping("/page")
    public ResultVO<MyPage<Trade>> page(@RequestBody @Valid PageTradeVO pageTradeVO) {
        MyPage<Trade> data = tradeService.pageTrade(pageTradeVO);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, data);
    }

    @ApiOperation("支付订单")
    @PostMapping("/pay")
    public ResultVO pay(@RequestParam String id, String moneyOrder) {
        tradeService.pay(id, moneyOrder);
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }


    @ApiOperation("更新订单状态")
    @PostMapping("/updateStatus")
    public ResultVO updateStatus(@RequestParam String id, @RequestParam Integer status) {
        tradeService.updateStatus(id, status);
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }

    @ApiOperation("订单详情")
    @PostMapping("/detail")
    public ResultVO<TradeDetailDataVO> detail(@RequestParam String id) {
        TradeDetailDataVO dataVO = tradeService.detail(id);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, dataVO);
    }

    @ApiOperation("匹配仓库")
    @PostMapping("/checkStore")
    public ResultVO<Map<Long, CheckStoreDataVO>> checkStore(@RequestBody CheckStoreVO checkStoreVO) {
        Map<Long, CheckStoreDataVO> data = tradeService.checkStore(checkStoreVO);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, data);
    }

    @ApiOperation("商品发货")
    @PostMapping("/goodsOut")
    public ResultVO goodsOut(@RequestBody @Valid GoodsOutVO goodsOutVO) {
        tradeService.goodsOut(goodsOutVO);
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }
}
