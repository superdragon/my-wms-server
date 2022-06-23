package com.jiaming.wms.goodsunit.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jiaming.wms.common.response.ResultCodeEnum;
import com.jiaming.wms.common.response.ResultVO;
import com.jiaming.wms.goodsunit.bean.entity.GoodsPack;
import com.jiaming.wms.goodsunit.service.IGoodsPackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dragon
 */
@Api(tags = "商品包装API")
@RestController
@RequestMapping("/goodsPack")
public class GoodsPackController {

    @Resource
    IGoodsPackService goodsPackService;

    @ApiOperation("保存商品包装")
    @PostMapping("/save")
    public ResultVO save(@RequestParam String name) {
        GoodsPack goodsPack = new GoodsPack();
        goodsPack.setName(name);
        goodsPackService.save(goodsPack);
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }

    @ApiOperation("更新商品包装")
    @PostMapping("/update")
    public ResultVO update(@RequestParam Long id,
                           @RequestParam String name,
                           @RequestParam Integer status) {
        GoodsPack goodsPack = new GoodsPack();
        goodsPack.setId(id);
        goodsPack.setName(name);
        goodsPack.setStatus(status);
        goodsPackService.updateById(goodsPack);
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }

    @ApiOperation("获取商品品牌列表")
    @RequestMapping("/find")
    public ResultVO<List<GoodsPack>> find() {
        QueryWrapper<GoodsPack> query = Wrappers.query();
        query.orderByDesc("id");
        List<GoodsPack> goodsPacks = goodsPackService.list(query);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, goodsPacks);
    }
}
