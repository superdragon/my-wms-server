package com.jiaming.wms.stat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaming.wms.stat.bean.entity.StatGoods;

/**
 * @author dragon
 */
public interface IStatGoodsService extends IService<StatGoods> {
    void initData();

}
