package com.jiaming.wms.goodsin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.goodsin.bean.entity.InStoreList;
import com.jiaming.wms.goodsin.bean.vo.AddInStoreListVO;
import com.jiaming.wms.goodsin.bean.vo.InfoInStoreListDataVO;
import com.jiaming.wms.goodsin.bean.vo.PageInStoreListDataVO;
import com.jiaming.wms.goodsin.bean.vo.PageInStoreListVO;

/**
 * @author dragon
 */
public interface IInStoreListService extends IService<InStoreList> {
    void saveInStore(AddInStoreListVO listVO);

    void removeInStore(String id);

    MyPage<PageInStoreListDataVO> pageInStore(PageInStoreListVO listVO);

    InfoInStoreListDataVO info(String id);
}
