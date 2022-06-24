package com.jiaming.wms.safestore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jiaming.wms.safestore.bean.entity.SafeStore;
import com.jiaming.wms.safestore.bean.vo.PageSafeStoreDataVO;
import com.jiaming.wms.safestore.bean.vo.PageSafeStoreVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author dragon
 */
@Mapper
public interface SafeStoreMapper extends BaseMapper<SafeStore> {
    IPage<PageSafeStoreDataVO> myPage(IPage<?> page,
                                      @Param("filter") PageSafeStoreVO safeStoreVO);
}
