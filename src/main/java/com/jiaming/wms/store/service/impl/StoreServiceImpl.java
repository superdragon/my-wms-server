package com.jiaming.wms.store.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.store.bean.entity.Store;
import com.jiaming.wms.store.bean.vo.PageStoreDataVO;
import com.jiaming.wms.store.bean.vo.PageStoreVO;
import com.jiaming.wms.store.mapper.StoreMapper;
import com.jiaming.wms.store.service.IStoreService;
import org.springframework.stereotype.Service;

/**
 * @author dragon
 */
@Service
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements IStoreService {
    @Override
    public MyPage<PageStoreDataVO> myPage(PageStoreVO storeVO) {

        IPage<PageStoreDataVO> page = new Page<>();
        page.setSize(storeVO.getPageSize());
        page.setCurrent(storeVO.getPageNum());

        this.baseMapper.myPage(page, storeVO);

        MyPage<PageStoreDataVO> result = new MyPage<>();
        result.setTotalNum(page.getTotal());
        result.setPageNum(page.getPages());
        result.setPageSize(page.getSize());
        result.setPageNum(page.getCurrent());
        result.setItems(page.getRecords());
        return result;
    }
}
