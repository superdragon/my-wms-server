package com.jiaming.wms.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jiaming.wms.goods.bean.entity.Goods;
import com.jiaming.wms.goods.bean.vo.FilterGoodsDataVO;
import com.jiaming.wms.goods.bean.vo.FilterGoodsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author dragon
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
    IPage<FilterGoodsDataVO> myPage(IPage<?> page, @Param("filter") FilterGoodsVO goodsVO);
}
