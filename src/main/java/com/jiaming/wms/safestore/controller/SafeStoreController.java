package com.jiaming.wms.safestore.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.common.exception.ApiException;
import com.jiaming.wms.common.response.ResultCodeEnum;
import com.jiaming.wms.common.response.ResultVO;
import com.jiaming.wms.safestore.bean.entity.SafeStore;
import com.jiaming.wms.safestore.bean.vo.AddSafeStoreVO;
import com.jiaming.wms.safestore.bean.vo.PageSafeStoreDataVO;
import com.jiaming.wms.safestore.bean.vo.PageSafeStoreVO;
import com.jiaming.wms.safestore.bean.vo.UpdateSafeStoreVO;
import com.jiaming.wms.safestore.service.ISafeStoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author dragon
 */
@Api(tags = "安全库存API")
@RestController
@RequestMapping("/safeStore")
public class SafeStoreController {

    @Autowired
    ISafeStoreService safeStoreService;

    @ApiOperation("添加商品安全库存")
    @PostMapping("/add")
        // 查询是否已经配置指定商品的安全库存
        public ResultVO add(@RequestBody @Valid AddSafeStoreVO safeStoreVO) {
        QueryWrapper<SafeStore> wrapper = Wrappers.query();
        wrapper.eq("store_id", safeStoreVO.getStoreId());
        wrapper.eq("goods_id", safeStoreVO.getGoodsId());
        wrapper.last("LIMIT 1");
        SafeStore temp = safeStoreService.getOne(wrapper);
        if (temp != null) { // 数据库已存在
            throw new ApiException("当前商品已配置过安全库存!");
        }
        SafeStore safeStore = new SafeStore();
        BeanUtils.copyProperties(safeStoreVO, safeStore);
        safeStoreService.save(safeStore);
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }

    @ApiOperation("更新商品安全库存")
    @PostMapping("/update")
    public ResultVO update(@RequestBody @Valid UpdateSafeStoreVO safeStoreVO) {
        SafeStore safeStore = new SafeStore();
        BeanUtils.copyProperties(safeStoreVO, safeStore);
        safeStoreService.updateById(safeStore);
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }

    @ApiOperation("分页查询商品安全库存")
    @PostMapping("/page")
    public ResultVO<MyPage<PageSafeStoreDataVO>> page(@RequestBody @Valid PageSafeStoreVO safeStoreVO) {
        MyPage<PageSafeStoreDataVO> data = safeStoreService.myPage(safeStoreVO);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, data);
    }

    @ApiOperation("删除商品安全库存")
    @PostMapping("/remove")
    public ResultVO remove(@RequestParam Long id) {
        safeStoreService.removeById(id);
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }
}
