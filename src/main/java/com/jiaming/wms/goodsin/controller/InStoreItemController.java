package com.jiaming.wms.goodsin.controller;

import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.common.response.ResultCodeEnum;
import com.jiaming.wms.common.response.ResultVO;
import com.jiaming.wms.goodsin.bean.vo.PageInStoreGoodsDataVO;
import com.jiaming.wms.goodsin.bean.vo.PageInStoreGoodsVO;
import com.jiaming.wms.goodsin.service.IInStoreItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author dragon
 */
@Api(tags = "入库清单明细API")
@RestController
@RequestMapping("/inStoreItem")
public class InStoreItemController {

    @Autowired
    IInStoreItemService inStoreItemService;

    @ApiOperation("删除清单明细")
    @PostMapping("/remove")
    public ResultVO remove(@RequestParam Long storeId, @RequestParam Long itemId) {
        // 删除某入库清单商品
        inStoreItemService.removeAndStatById(storeId, itemId);
        return new ResultVO(ResultCodeEnum.SUCCESS);
    }

    @ApiOperation("分页查看清单明细")
    @PostMapping("/page")
    public ResultVO<MyPage<PageInStoreGoodsDataVO>> page(@RequestBody @Valid PageInStoreGoodsVO pageInStoreGoodsVO) {
        MyPage<PageInStoreGoodsDataVO> data = inStoreItemService.myPage(pageInStoreGoodsVO);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, data);
    }
}
