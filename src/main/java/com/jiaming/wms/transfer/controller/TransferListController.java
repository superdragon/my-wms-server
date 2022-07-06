package com.jiaming.wms.transfer.controller;

import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.common.response.ResultCodeEnum;
import com.jiaming.wms.common.response.ResultVO;
import com.jiaming.wms.transfer.bean.vo.AddTransferListVO;
import com.jiaming.wms.transfer.bean.vo.PageTransferListDataVO;
import com.jiaming.wms.transfer.bean.vo.PageTransferListVO;
import com.jiaming.wms.transfer.service.ITransferListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author dragon
 */
@Api(tags = "库存调拨API")
@RestController
@RequestMapping("/transferList")
public class TransferListController {

    @Autowired
    ITransferListService transferListService;

    @ApiOperation("添加调拨订单")
    @PostMapping("/add")
    public ResultVO add(@RequestBody @Valid AddTransferListVO addTransferListVO) {
        transferListService.saveTransferList(addTransferListVO);
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }

    @ApiOperation("分页查看调拨订单")
    @PostMapping("/page")
    public ResultVO<MyPage<PageTransferListDataVO>> page(@RequestBody @Valid PageTransferListVO listVO) {
        MyPage<PageTransferListDataVO> data = transferListService.pageTransferList(listVO);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, data);
    }

    @ApiOperation("更新调拨订单状态")
    @PostMapping("/updateStatus")
    public ResultVO updateStatus(@RequestParam String id, @RequestParam Integer status) {
        transferListService.updateStatus(id, status);
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }
}
