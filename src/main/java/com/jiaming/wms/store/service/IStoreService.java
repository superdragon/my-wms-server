package com.jiaming.wms.store.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.store.bean.entity.Store;
import com.jiaming.wms.store.bean.vo.PageStoreDataVO;
import com.jiaming.wms.store.bean.vo.PageStoreVO;

/**
 * @author dragon
 */
public interface IStoreService extends IService<Store> {
    MyPage<PageStoreDataVO> myPage(PageStoreVO storeVO);
}
