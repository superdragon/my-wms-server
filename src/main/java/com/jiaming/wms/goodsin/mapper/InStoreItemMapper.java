package com.jiaming.wms.goodsin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jiaming.wms.goodsin.bean.entity.InStoreItem;
import com.jiaming.wms.goodsin.bean.vo.PageInStoreGoodsDataVO;
import com.jiaming.wms.goodsin.bean.vo.PageInStoreGoodsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author dragon
 */
@Mapper
public interface InStoreItemMapper extends BaseMapper<InStoreItem> {
    IPage<PageInStoreGoodsDataVO> myPage(IPage<?> page,
                                         @Param("filter") PageInStoreGoodsVO pageInStoreGoodsVO);
}
