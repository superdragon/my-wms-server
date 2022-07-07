package com.jiaming.wms.stat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jiaming.wms.stat.bean.entity.StoreGoodsStat;
import com.jiaming.wms.stat.bean.vo.PageStoreGoodsStatDataVO;
import com.jiaming.wms.stat.bean.vo.PageStoreGoodsStatVO;
import com.jiaming.wms.stat.bean.vo.StockCheckStatDataVO;
import com.jiaming.wms.stat.bean.vo.StoreGoodsStatDataVO;
import com.jiaming.wms.stat.bo.StoreGoodsInitDataBO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author dragon
 */
@Mapper
public interface StoreGoodsStatMapper extends BaseMapper<StoreGoodsStat> {
    IPage<PageStoreGoodsStatDataVO> myPage(IPage<?> page,
                                           @Param("filter") PageStoreGoodsStatVO goodsStatVO);

    List<StoreGoodsStatDataVO> getGoods(Long storeId);

    void lockGoodsNum(@Param("storeId") Long shipStoreId,
                      @Param("goodsId") Long goodsId,
                      @Param("goodsNum") Long goodsNum);

    void updateOutTotal(@Param("storeId") Long shipStoreId,
                        @Param("goodsId") Long goodsId,
                        @Param("goodsNum") Long goodsNum);

    void reduceOuttingTotal(@Param("storeId") Long shipStoreId,
                            @Param("goodsId") Long goodsId,
                            @Param("goodsNum") Long goodsNum);

    void reduceOutTotal(@Param("storeId") Long shipStoreId,
                        @Param("goodsId") Long goodsId,
                        @Param("goodsNum") Long goodsNum);

    void plusOutTotal(@Param("storeId") Long shipStoreId,
                      @Param("goodsId") Long goodsId,
                      @Param("goodsNum") Long goodsNum);

    StockCheckStatDataVO stat(Long id);

    List<StoreGoodsInitDataBO> getAllGoods();
}
