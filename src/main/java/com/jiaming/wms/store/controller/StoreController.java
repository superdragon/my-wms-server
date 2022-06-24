package com.jiaming.wms.store.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.common.response.ResultCodeEnum;
import com.jiaming.wms.common.response.ResultVO;
import com.jiaming.wms.store.bean.entity.Store;
import com.jiaming.wms.store.bean.vo.AddStoreVO;
import com.jiaming.wms.store.bean.vo.PageStoreDataVO;
import com.jiaming.wms.store.bean.vo.PageStoreVO;
import com.jiaming.wms.store.bean.vo.UpdateStoreVO;
import com.jiaming.wms.store.service.IStoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author dragon
 */
@Api(tags = "仓库API")
@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    IStoreService storeService;

    @ApiOperation("添加仓库")
    @PostMapping("/add")
    public ResultVO add(@RequestBody @Valid AddStoreVO storeVO) {
        Store store = new Store();
        BeanUtils.copyProperties(storeVO, store);
        storeService.save(store);
        return new ResultVO(ResultCodeEnum.SUCCESS);
    }

    @ApiOperation("更新仓库")
    @PostMapping("/update")
    public ResultVO update(@RequestBody @Valid UpdateStoreVO storeVO) {
        Store store = new Store();
        BeanUtils.copyProperties(storeVO, store);
        storeService.updateById(store);
        return new ResultVO(ResultCodeEnum.SUCCESS);
    }

    @ApiOperation("获取仓库分页列表")
    @PostMapping("/page")
    public ResultVO<MyPage<PageStoreDataVO>> page(@RequestBody @Valid PageStoreVO storeVO) {
        MyPage<PageStoreDataVO> data = storeService.myPage(storeVO);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, data);
    }

    @ApiOperation("获取仓库列表")
    @PostMapping("/list")
    public ResultVO<List<Store>> list(Integer status) {
        QueryWrapper<Store> wrapper = Wrappers.query();
        if (status != null) {
            wrapper.eq("status", status);
        }
        return new ResultVO<>(ResultCodeEnum.SUCCESS, storeService.list(wrapper));
    }

    @ApiOperation("获取单个仓库信息")
    @PostMapping("/get")
    public ResultVO<Store> get(@RequestParam Long id) {
        return new ResultVO<>(ResultCodeEnum.SUCCESS, storeService.getById(id));
    }
}
