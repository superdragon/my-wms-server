package com.jiaming.wms.region.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jiaming.wms.common.response.ResultCodeEnum;
import com.jiaming.wms.common.response.ResultVO;
import com.jiaming.wms.region.bean.entity.Region;
import com.jiaming.wms.region.service.IRegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author dragon
 */
@Api(tags = "区域API")
@RestController
@RequestMapping("/region")
public class RegionController {

    @Autowired
    IRegionService regionService;

    @ApiOperation("获取省份数据")
    @PostMapping("/prov")
    public ResultVO<List<Region>> prov() {
        QueryWrapper<Region> wrapper = Wrappers.query();
        wrapper.eq("level_id", 1);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, regionService.list(wrapper));
    }

    @ApiOperation("获取城市数据")
    @PostMapping("/city")
    public ResultVO<List<Region>> city(@RequestParam Long provId) {
        QueryWrapper<Region> wrapper = Wrappers.query();
        wrapper.eq("parent_id", provId);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, regionService.list(wrapper));
    }
}
