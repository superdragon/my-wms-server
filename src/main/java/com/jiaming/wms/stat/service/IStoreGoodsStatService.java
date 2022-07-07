package com.jiaming.wms.stat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.stat.bean.entity.StoreGoodsStat;
import com.jiaming.wms.stat.bean.vo.PageStoreGoodsStatDataVO;
import com.jiaming.wms.stat.bean.vo.PageStoreGoodsStatVO;
import com.jiaming.wms.stat.bean.vo.StockCheckStatDataVO;
import com.jiaming.wms.stat.bean.vo.StoreGoodsStatDataVO;
import com.jiaming.wms.stat.bo.StoreGoodsInitDataBO;

import java.util.List;

/**
 * @author dragon
 */
public interface IStoreGoodsStatService extends IService<StoreGoodsStat> {
    StoreGoodsStat getByGoodsIdAndStoreId(Long goodsId, Long storeId);

    MyPage<PageStoreGoodsStatDataVO> myPage(PageStoreGoodsStatVO goodsStatVO);

    List<StoreGoodsStatDataVO> getGoods(Long storeId);

    void lockGoodsNum(Long shipStoreId, Long goodsId, Long goodsNum);

    void updateOutTotal(Long shipStoreId, Long goodsId, Long goodsNum);

    void increaseInTotal(Long receiveStoreId, Long goodsId, Long goodsNum);

    void reduceOuttingTotal(Long shipStoreId, Long goodsId, Long goodsNum);

    void reduceOutTotal(Long shipStoreId, Long goodsId, Long goodsNum);

    List<StoreGoodsStat> getGoodsNumByStoreId(Long storeId, List<Long> goodsIdList);

    void plusOutTotal(Long storeId, Long goodsId, Long goodsNum);

    void reduceInTotal(Long storeId, Long goodsId, Long goodsNum);

    List<StockCheckStatDataVO> stat();

    List<StoreGoodsInitDataBO> getAllGoods();
}
