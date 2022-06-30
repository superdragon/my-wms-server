package com.jiaming.wms.stat.controller;

import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.common.response.ResultCodeEnum;
import com.jiaming.wms.common.response.ResultVO;
import com.jiaming.wms.stat.bean.entity.StoreGoodsStat;
import com.jiaming.wms.stat.bean.vo.PageStoreGoodsStatDataVO;
import com.jiaming.wms.stat.bean.vo.PageStoreGoodsStatVO;
import com.jiaming.wms.stat.bean.vo.StockCheckStatDataVO;
import com.jiaming.wms.stat.bean.vo.StoreGoodsStatDataVO;
import com.jiaming.wms.stat.service.IStoreGoodsStatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dragon
 */
@Api(tags = "库存盘点API")
@RestController
@RequestMapping("/goodsStat")
public class StoreGoodsStatController {

    @Autowired
    IStoreGoodsStatService storeGoodsStatService;

    @ApiOperation("仓储商品库存统计")
    @PostMapping("/stat")
    public ResultVO<List<StockCheckStatDataVO>> stat() {
        List<StockCheckStatDataVO> data = storeGoodsStatService.stat();
        return new ResultVO<>(ResultCodeEnum.SUCCESS, data);
    }

    @ApiOperation("分页查看仓储商品库存")
    @PostMapping("/page")
    public ResultVO<MyPage<PageStoreGoodsStatDataVO>> page(@RequestBody @Valid PageStoreGoodsStatVO goodsStatVO) {
        MyPage<PageStoreGoodsStatDataVO> data = storeGoodsStatService.myPage(goodsStatVO);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, data);
    }

    @ApiOperation("查看仓储所有商品库存")
    @PostMapping("/getGoods")
    public ResultVO<List<StoreGoodsStatDataVO>> getGoods(@RequestParam Long storeId) {
        List<StoreGoodsStatDataVO> goodsList = storeGoodsStatService.getGoods(storeId);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, goodsList);
    }

    @ApiOperation("查看仓储商品库存")
    @PostMapping("/getNumByTrade")
    public ResultVO<List<StoreGoodsStat>> getNumByTrade(@RequestParam Long storeId,
                                                        @RequestParam String goodsIds) {
        String[] goodsIdstr = goodsIds.split(",");
        List<Long> goodsIdList = new ArrayList<>();
        for (String s : goodsIdstr) {
            goodsIdList.add(Long.parseLong(s));
        }
        List<StoreGoodsStat> data = storeGoodsStatService.getNumByTrade(storeId, goodsIdList);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, data);
    }
}
