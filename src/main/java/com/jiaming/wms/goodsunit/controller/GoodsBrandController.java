package com.jiaming.wms.goodsunit.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jiaming.wms.common.response.ResultCodeEnum;
import com.jiaming.wms.common.response.ResultVO;
import com.jiaming.wms.goodsunit.bean.entity.GoodsBrand;
import com.jiaming.wms.goodsunit.service.IGoodsBrandService;
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
@Api(tags = "商品品牌API")
@RestController
@RequestMapping("/goodsBrand")
public class GoodsBrandController {

    @Resource
    IGoodsBrandService brandService;

    @ApiOperation("保存商品品牌")
    @PostMapping("/save")
    public ResultVO save(@RequestParam String name) {
        GoodsBrand goodsBrand = new GoodsBrand();
        goodsBrand.setName(name);
        brandService.save(goodsBrand);
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }

    @ApiOperation("更新商品品牌")
    @PostMapping("/update")
    public ResultVO update(@RequestParam Long id,
                           @RequestParam String name,
                           @RequestParam Integer status) {
        GoodsBrand goodsBrand = new GoodsBrand();
        goodsBrand.setId(id);
        goodsBrand.setName(name);
        goodsBrand.setStatus(status);
        brandService.updateById(goodsBrand);
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }

    @ApiOperation("获取品牌信息")
    @RequestMapping("/find")
    public ResultVO<List<GoodsBrand>> find(Integer status) {
        QueryWrapper<GoodsBrand> query = Wrappers.query();
        query.orderByDesc("id");
        if (status != null) {
            query.eq("status", status);
        }
        List<GoodsBrand> goodsBrands = brandService.list(query);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, goodsBrands);
    }
}
