package com.jiaming.wms.goods.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.common.exception.ApiException;
import com.jiaming.wms.common.response.ResultCodeEnum;
import com.jiaming.wms.common.response.ResultVO;
import com.jiaming.wms.goods.bean.entity.Goods;
import com.jiaming.wms.goods.bean.vo.*;
import com.jiaming.wms.goods.service.IGoodsService;
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
@Api(tags = "商品API")
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    IGoodsService goodsService;

    @ApiOperation("保存商品信息")
    @PostMapping("/save")
    public ResultVO save(@RequestBody @Valid AddGoodsVO goodsVO) {
        // 校验商品编码是否已使用
        QueryWrapper<Goods> queryWrapper = Wrappers.query();
        queryWrapper.eq("code", goodsVO.getCode()).last("LIMIT 1");
        Goods goods = goodsService.getOne(queryWrapper);
        if (goods != null) {
            throw new ApiException("商品编码已使用");
        }
        // 调用service层的save方法
        goodsService.save(goodsVO);
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }

    @ApiOperation("更新商品信息")
    @PostMapping("/update")
    public ResultVO update(@RequestBody @Valid UpdateGoodsVO goodsVO) {
        // 校验更新的商品编码是否已使用
        QueryWrapper<Goods> queryWrapper = Wrappers.query();
        queryWrapper.eq("code", goodsVO.getCode())
                .ne("id", goodsVO.getId()).last("LIMIT 1");
        Goods goods = goodsService.getOne(queryWrapper);
        if (goods != null) {
            throw new ApiException("商品编码已使用..");
        }
        // 调用service层的update方法
        goodsService.update(goodsVO);
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }

    @ApiOperation("分页查看商品信息")
    @PostMapping("/page")
    public ResultVO<MyPage<FilterGoodsDataVO>> page(@RequestBody @Valid FilterGoodsVO goodsVO) {
        MyPage<FilterGoodsDataVO> page = goodsService.myPage(goodsVO);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, page);
    }

    @ApiOperation("更新商品状态")
    @PostMapping("/updateStatus")
    public ResultVO updateStatus(@RequestParam String ids,
                                 @RequestParam Integer status) {
        String[] idArr = ids.split(",");
        List<Long> idList = new ArrayList<>();
        for (String s : idArr) {
            idList.add(Long.parseLong(s));
        }
        goodsService.updateStatus(idList, status);
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }

    @RequestMapping("/get")
    public ResultVO<GoodsDataVO> getById(@RequestParam Long id) {
        GoodsDataVO goods = goodsService.getInfoById(id);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, goods);
    }
    @PostMapping("/list")
    public ResultVO<List<Goods>> list(@RequestParam Integer status) {
        QueryWrapper<Goods> wrapper = Wrappers.query();
        wrapper.eq("status", status);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, goodsService.list(wrapper));
    }
}
