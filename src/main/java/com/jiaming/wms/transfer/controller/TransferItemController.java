package com.jiaming.wms.transfer.controller;

import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.common.response.ResultCodeEnum;
import com.jiaming.wms.common.response.ResultVO;
import com.jiaming.wms.transfer.bean.vo.PageTransferItemDataVO;
import com.jiaming.wms.transfer.bean.vo.PageTransferItemVO;
import com.jiaming.wms.transfer.service.ITransferItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author dragon
 */
@Api(tags = "调拨清单明细API")
@RestController
@RequestMapping("/transferItem")
public class TransferItemController {

    @Autowired
    ITransferItemService transferItemService;

    @ApiOperation("分页查看清单明细")
    @PostMapping("/page")
    public ResultVO<MyPage<PageTransferItemDataVO>> page(@RequestBody @Valid PageTransferItemVO pageTransferItemVO) {
        MyPage<PageTransferItemDataVO> data = transferItemService.myPage(pageTransferItemVO);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, data);
    }
}
