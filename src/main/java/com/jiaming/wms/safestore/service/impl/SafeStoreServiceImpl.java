package com.jiaming.wms.safestore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.safestore.bean.entity.SafeStore;
import com.jiaming.wms.safestore.bean.vo.PageSafeStoreDataVO;
import com.jiaming.wms.safestore.bean.vo.PageSafeStoreVO;
import com.jiaming.wms.safestore.mapper.SafeStoreMapper;
import com.jiaming.wms.safestore.service.ISafeStoreService;
import org.springframework.stereotype.Service;

/**
 * @author dragon
 */
@Service
public class SafeStoreServiceImpl extends ServiceImpl<SafeStoreMapper, SafeStore> implements ISafeStoreService {
    @Override
    public MyPage<PageSafeStoreDataVO> myPage(PageSafeStoreVO safeStoreVO) {

        IPage<PageSafeStoreDataVO> page = new Page<>();
        page.setSize(safeStoreVO.getPageSize());
        page.setCurrent(safeStoreVO.getPageNum());

        this.baseMapper.myPage(page, safeStoreVO);

        MyPage<PageSafeStoreDataVO> result = new MyPage<>();
        result.setTotalNum(page.getTotal());
        result.setPageNum(page.getPages());
        result.setPageSize(page.getSize());
        result.setPageNum(page.getCurrent());
        result.setItems(page.getRecords());
        return result;
    }

    // 获取商品在某个仓库的安全库存阈值
    @Override
    public long getByGoodsIdAndStoreId(Long goodsId, Long storeId) {
        QueryWrapper<SafeStore> wrapper = Wrappers.query();
        wrapper.eq("goods_id", goodsId);
        wrapper.eq("store_id", storeId);
        wrapper.last("LIMIT 1");
        SafeStore safeStore = this.getOne(wrapper);
        if (safeStore != null) {
            return safeStore.getSafeNum();
        }
        return 0;
    }
}
