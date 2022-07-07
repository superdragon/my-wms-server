package com.jiaming.wms.stat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaming.wms.stat.bean.entity.StatTrade;
import com.jiaming.wms.stat.mapper.StatTradeMapper;
import com.jiaming.wms.stat.service.IStatTradeService;
import org.springframework.stereotype.Service;

/**
 * @author dragon
 */
@Service
public class StatTradeServiceImpl extends ServiceImpl<StatTradeMapper, StatTrade> implements IStatTradeService {
}
