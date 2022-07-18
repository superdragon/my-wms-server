package com.jiaming.wms.trade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaming.wms.trade.bean.entity.TradeItem;
import com.jiaming.wms.trade.mapper.TradeItemMapper;
import com.jiaming.wms.trade.service.ITradeItemService;
import org.springframework.stereotype.Service;

/**
 * @author dragon
 */
@Service
public class TradeItemServiceImpl extends ServiceImpl<TradeItemMapper, TradeItem>
        implements ITradeItemService {
}
