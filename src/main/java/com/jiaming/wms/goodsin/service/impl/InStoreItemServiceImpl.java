package com.jiaming.wms.goodsin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.goodsin.bean.entity.InStoreItem;
import com.jiaming.wms.goodsin.bean.vo.PageInStoreGoodsDataVO;
import com.jiaming.wms.goodsin.bean.vo.PageInStoreGoodsVO;
import com.jiaming.wms.goodsin.mapper.InStoreItemMapper;
import com.jiaming.wms.goodsin.service.IInStoreItemService;
import com.jiaming.wms.stat.service.IStoreGoodsStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dragon
 */
@Service
public class InStoreItemServiceImpl extends ServiceImpl<InStoreItemMapper, InStoreItem> implements IInStoreItemService {
    @Autowired
    IStoreGoodsStatService storeGoodsStatService;

    @Override
    public MyPage<PageInStoreGoodsDataVO> myPage(PageInStoreGoodsVO pageInStoreGoodsVO) {
        IPage<PageInStoreGoodsDataVO> page = new Page<>();
        page.setSize(pageInStoreGoodsVO.getPageSize());
        page.setCurrent(pageInStoreGoodsVO.getPageNum());

        this.baseMapper.myPage(page, pageInStoreGoodsVO);

        MyPage<PageInStoreGoodsDataVO> result = new MyPage<>();
        result.setTotalNum(page.getTotal());
        result.setTotalPage(page.getPages());
        result.setPageSize(page.getSize());
        result.setPageNum(page.getCurrent());
        result.setItems(page.getRecords());
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeAndStatById(Long storeId, Long itemId) {
        InStoreItem inStoreItem = this.getById(itemId);
        if (inStoreItem != null) {
            storeGoodsStatService.reduceInTotal(storeId, inStoreItem.getGoodsId(), inStoreItem.getGoodsNum());
        }
        this.removeById(itemId);
    }
}
