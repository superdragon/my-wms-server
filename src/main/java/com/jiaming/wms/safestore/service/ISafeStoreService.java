package com.jiaming.wms.safestore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.safestore.bean.entity.SafeStore;
import com.jiaming.wms.safestore.bean.vo.PageSafeStoreDataVO;
import com.jiaming.wms.safestore.bean.vo.PageSafeStoreVO;

/**
 * @author dragon
 */
public interface ISafeStoreService extends IService<SafeStore> {
    MyPage<PageSafeStoreDataVO> myPage(PageSafeStoreVO safeStoreVO);

    long getByGoodsIdAndStoreId(Long goodsId, Long storeId);
}
