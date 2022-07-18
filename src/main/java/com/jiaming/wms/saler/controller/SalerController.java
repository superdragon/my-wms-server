package com.jiaming.wms.saler.controller;

import com.jiaming.wms.common.response.ResultCodeEnum;
import com.jiaming.wms.common.response.ResultVO;
import com.jiaming.wms.saler.bean.entity.Saler;
import com.jiaming.wms.saler.service.ISalerService;
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
@Api(tags = "销售经理API")
@RestController
@RequestMapping("/saler")
public class SalerController {

    @Autowired
    ISalerService salerService;

    @ApiOperation("分页获取销售信息")
    @PostMapping("/list")
    public ResultVO<List<Saler>> list() {
        return new ResultVO<>(ResultCodeEnum.SUCCESS, salerService.list());
    }

    @ApiOperation("获取销售信息")
    @PostMapping("/get")
    public ResultVO<Saler> get(@RequestParam Long id) {
        return new ResultVO<>(ResultCodeEnum.SUCCESS, salerService.getById(id));
    }
}
