package com.jiaming.wms.stat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaming.wms.stat.bean.entity.StatGoods;
import com.jiaming.wms.stat.bean.vo.LatestGoodsTopDataVO;

import java.util.List;

/**
 * @author dragon
 */
public interface IStatGoodsService extends IService<StatGoods> {
    void initData();

    void statData();

    List<LatestGoodsTopDataVO> latestGoodsTop();
}
