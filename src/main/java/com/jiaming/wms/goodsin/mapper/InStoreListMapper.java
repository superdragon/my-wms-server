package com.jiaming.wms.goodsin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jiaming.wms.goodsin.bean.entity.InStoreList;
import com.jiaming.wms.goodsin.bean.vo.PageInStoreListDataVO;
import com.jiaming.wms.goodsin.bean.vo.PageInStoreListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author dragon
 */
@Mapper
public interface InStoreListMapper extends BaseMapper<InStoreList> {
    IPage<PageInStoreListDataVO> myPage(IPage<?> page,
                                        @Param("filter") PageInStoreListVO listVO);
}
