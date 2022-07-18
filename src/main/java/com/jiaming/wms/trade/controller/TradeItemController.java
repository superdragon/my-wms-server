package com.jiaming.wms.trade.controller;

import com.jiaming.wms.trade.service.ITradeItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dragon
 */
@RestController
@RequestMapping("/tradeItem")
public class TradeItemController {
    @Autowired
    ITradeItemService tradeItemService;
}
