package com.jiaming.wms.goodsin.controller;

import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.common.response.ResultCodeEnum;
import com.jiaming.wms.common.response.ResultVO;
import com.jiaming.wms.goodsin.bean.vo.AddInStoreListVO;
import com.jiaming.wms.goodsin.bean.vo.InfoInStoreListDataVO;
import com.jiaming.wms.goodsin.bean.vo.PageInStoreListDataVO;
import com.jiaming.wms.goodsin.bean.vo.PageInStoreListVO;
import com.jiaming.wms.goodsin.service.IInStoreListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author dragon
 */
@Api(tags = "入库API")
@RestController
@RequestMapping("/inStoreList")
public class InStoreListController {

    @Autowired
    IInStoreListService inStoreListService;

    @ApiOperation("创建入库清单")
    @PostMapping("/add")
    public ResultVO add(@RequestBody @Valid AddInStoreListVO listVO) {
        inStoreListService.saveInStore(listVO);
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }

    @ApiOperation("删除入库清单")
    @PostMapping("/remove")
    public ResultVO remove(@RequestParam String id) {
        inStoreListService.removeInStore(id);
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }

    @ApiOperation("分页查看入库清单")
    @PostMapping("/page")
    public ResultVO<MyPage<PageInStoreListDataVO>> page(@RequestBody @Valid PageInStoreListVO listVO) {
        MyPage<PageInStoreListDataVO> data = inStoreListService.pageInStore(listVO);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, data);
    }

    @ApiOperation("查看入库清单详情")
    @PostMapping("/info")
    public ResultVO<InfoInStoreListDataVO> info(@RequestParam String id) {
        InfoInStoreListDataVO data = inStoreListService.info(id);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, data);
    }
}
