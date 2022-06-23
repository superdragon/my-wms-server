package com.jiaming.wms.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.goods.bean.entity.Goods;
import com.jiaming.wms.goods.bean.vo.*;

import java.util.List;

/**
 * @author dragon
 */
public interface IGoodsService extends IService<Goods> {
    void save(AddGoodsVO goodsVO);

    MyPage<FilterGoodsDataVO> myPage(FilterGoodsVO goodsVO);

    GoodsDataVO getInfoById(Long id);

    void update(UpdateGoodsVO goodsVO);

    void updateStatus(List<Long> ids, Integer status);
}
