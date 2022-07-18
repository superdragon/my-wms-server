package com.jiaming.wms.stat.controller;

import com.jiaming.wms.common.response.ResultCodeEnum;
import com.jiaming.wms.common.response.ResultVO;
import com.jiaming.wms.stat.bean.vo.LatestGoodsTopDataVO;
import com.jiaming.wms.stat.service.IStatGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author dragon
 */
@Api(tags = "商品统计API")
@RestController
@RequestMapping("/statGoods")
public class StatGoodsController {

    @Autowired
    IStatGoodsService statGoodsService;

    @ApiOperation("初始化数据(仅测试用)")
    @GetMapping("/initData")
    public String initData() {
        statGoodsService.initData();
        return "SUCCESS";
    }

    @ApiOperation("获取近30日商品排行榜")
    @GetMapping("/latestGoodsTop")
    public ResultVO<List<LatestGoodsTopDataVO>> latestGoodsTop() {
        List<LatestGoodsTopDataVO> data = statGoodsService.latestGoodsTop();
        return new ResultVO<>(ResultCodeEnum.SUCCESS, data);
    }
}
