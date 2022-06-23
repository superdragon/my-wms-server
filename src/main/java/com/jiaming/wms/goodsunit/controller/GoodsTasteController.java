package com.jiaming.wms.goodsunit.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jiaming.wms.common.response.ResultCodeEnum;
import com.jiaming.wms.common.response.ResultVO;
import com.jiaming.wms.goodsunit.bean.entity.GoodsTaste;
import com.jiaming.wms.goodsunit.service.IGoodsTasteService;
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
@Api(tags = "商品口味API")
@RestController
@RequestMapping("/goodsTaste")
public class GoodsTasteController {

    @Resource
    IGoodsTasteService goodsTasteService;

    @ApiOperation("保存商品口味")
    @PostMapping("/save")
    public ResultVO save(@RequestParam String name) {
        GoodsTaste goodsTaste = new GoodsTaste();
        goodsTaste.setName(name);
        goodsTasteService.save(goodsTaste);
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }

    @ApiOperation("更新商品口味")
    @PostMapping("/update")
    public ResultVO update(@RequestParam Long id,
                           @RequestParam String name,
                           @RequestParam Integer status) {
        GoodsTaste goodsTaste = new GoodsTaste();
        goodsTaste.setId(id);
        goodsTaste.setName(name);
        goodsTaste.setStatus(status);
        goodsTasteService.updateById(goodsTaste);
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }

    @ApiOperation("获取商品口味列表")
    @RequestMapping("/find")
    public ResultVO<List<GoodsTaste>> find() {
        QueryWrapper<GoodsTaste> query = Wrappers.query();
        query.orderByDesc("id");
        List<GoodsTaste> goodsTastes = goodsTasteService.list(query);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, goodsTastes);
    }
}
