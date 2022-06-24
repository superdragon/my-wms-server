package com.jiaming.wms.store.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jiaming.wms.store.bean.entity.Store;
import com.jiaming.wms.store.bean.vo.PageStoreDataVO;
import com.jiaming.wms.store.bean.vo.PageStoreVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author dragon
 */
@Mapper
public interface StoreMapper extends BaseMapper<Store> {
    IPage<PageStoreDataVO> myPage(IPage<?> page, @Param("filter") PageStoreVO storeVO);
}
