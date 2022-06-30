package com.jiaming.wms.goodsin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.goodsin.bean.entity.InStoreItem;
import com.jiaming.wms.goodsin.bean.vo.PageInStoreGoodsDataVO;
import com.jiaming.wms.goodsin.bean.vo.PageInStoreGoodsVO;

/**
 * @author dragon
 */
public interface IInStoreItemService extends IService<InStoreItem> {
    MyPage<PageInStoreGoodsDataVO> myPage(PageInStoreGoodsVO pageInStoreGoodsVO);

    void removeAndStatById(Long storeId, Long itemId);
}
