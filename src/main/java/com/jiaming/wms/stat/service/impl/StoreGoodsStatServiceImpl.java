package com.jiaming.wms.stat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.stat.bean.entity.StoreGoodsStat;
import com.jiaming.wms.stat.bean.vo.PageStoreGoodsStatDataVO;
import com.jiaming.wms.stat.bean.vo.PageStoreGoodsStatVO;
import com.jiaming.wms.stat.bean.vo.StockCheckStatDataVO;
import com.jiaming.wms.stat.bean.vo.StoreGoodsStatDataVO;
import com.jiaming.wms.stat.mapper.StoreGoodsStatMapper;
import com.jiaming.wms.stat.service.IStoreGoodsStatService;
import com.jiaming.wms.store.bean.entity.Store;
import com.jiaming.wms.store.service.IStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dragon
 */
@Service
public class StoreGoodsStatServiceImpl
        extends ServiceImpl<StoreGoodsStatMapper, StoreGoodsStat>
        implements IStoreGoodsStatService {

    @Autowired
    IStoreService storeService;

    @Override
    public StoreGoodsStat getByGoodsIdAndStoreId(Long goodsId, Long storeId) {
        QueryWrapper<StoreGoodsStat> query = Wrappers.query();
        query.eq("goods_id", goodsId);
        query.eq("store_id", storeId);
        query.last("LIMIT 1");
        return this.getOne(query);
    }

    @Override
    public MyPage<PageStoreGoodsStatDataVO> myPage(PageStoreGoodsStatVO goodsStatVO) {
        IPage<PageStoreGoodsStatDataVO> page = new Page<>();
        page.setSize(goodsStatVO.getPageSize());
        page.setCurrent(goodsStatVO.getPageNum());

        this.baseMapper.myPage(page, goodsStatVO);

        MyPage<PageStoreGoodsStatDataVO> result = new MyPage<>();
        result.setTotalNum(page.getTotal());
        result.setPageNum(page.getPages());
        result.setPageSize(page.getSize());
        result.setPageNum(page.getCurrent());
        result.setItems(page.getRecords());
        return result;
    }

    @Override
    public List<StoreGoodsStatDataVO> getGoods(Long storeId) {
        return this.baseMapper.getGoods(storeId);
    }

    /**
     * 锁定待发货商品的数量
     * @param shipStoreId 仓库ID
     * @param goodsId 代发货商品ID
     * @param goodsNum 锁定数量
     */
    @Override
    public void lockGoodsNum(Long shipStoreId, Long goodsId, Long goodsNum) {
        this.baseMapper.lockGoodsNum(shipStoreId, goodsId, goodsNum);
    }

    /**
     * 减去待出库商品数量，增加已出库商品数量
     * @param shipStoreId
     * @param goodsId
     * @param goodsNum
     */
    @Override
    public void updateOutTotal(Long shipStoreId, Long goodsId, Long goodsNum) {
        this.baseMapper.updateOutTotal(shipStoreId, goodsId, goodsNum);
    }

    @Override
    public void increaseInTotal(Long receiveStoreId, Long goodsId, Long goodsNum) {
        // 查询对应仓库以及商品的入库统计记录
        StoreGoodsStat storeGoodsStat = this.getByGoodsIdAndStoreId(goodsId, receiveStoreId);
        // 如果有，则把当前入库的总量增加上去，做update
        if (storeGoodsStat != null) {
            storeGoodsStat.setInTotal(storeGoodsStat.getInTotal() + goodsNum);
            this.updateById(storeGoodsStat);
        } else {
            // 如果没有，则把当前的入库商品添加，做save
            StoreGoodsStat newStat = new StoreGoodsStat();
            newStat.setGoodsId(goodsId);
            newStat.setStoreId(receiveStoreId);
            newStat.setInTotal(goodsNum);
            this.save(newStat);
        }
    }

    @Override
    public void reduceOuttingTotal(Long shipStoreId, Long goodsId, Long goodsNum) {
        this.baseMapper.reduceOuttingTotal(shipStoreId, goodsId, goodsNum);
    }

    @Override
    public void reduceOutTotal(Long shipStoreId, Long goodsId, Long goodsNum) {
        this.baseMapper.reduceOutTotal(shipStoreId, goodsId, goodsNum);
    }

    @Override
    public List<StoreGoodsStat> getGoodsNumByStoreId(Long storeId, List<Long> goodsIdList) {
        QueryWrapper<StoreGoodsStat> query = Wrappers.query();
        query.eq("store_id", storeId);
        query.in("goods_id", goodsIdList);
        return this.list(query);
    }

    @Override
    public void plusOutTotal(Long storeId, Long goodsId, Long goodsNum) {
        this.baseMapper.plusOutTotal(storeId, goodsId, goodsNum);
    }

    @Override
    public void reduceInTotal(Long storeId, Long goodsId, Long goodsNum) {
        // 查询对应仓库以及商品的入库统计记录
        StoreGoodsStat storeGoodsStat = this.getByGoodsIdAndStoreId(goodsId, storeId);
        // 如果有，则把当前入库的总量减去要移除此次入库商品数量，做update
        if (storeGoodsStat != null) {
            storeGoodsStat.setInTotal(storeGoodsStat.getInTotal() - goodsNum);
            if (storeGoodsStat.getInTotal() == 0) {
                // 如果商品库存为0，那么移除库存记录
                this.removeById(storeGoodsStat.getId());
            } else {
                // 如果商品库存不为0，那么做更新操作
                this.updateById(storeGoodsStat);
            }
        }
    }

    @Override
    public List<StockCheckStatDataVO> stat() {
        List<StockCheckStatDataVO> data = new ArrayList<>();

        // 获取所有仓库基本信息
        List<Store> stores = storeService.list();
        // 计算每个仓库中每个商品的总量和总价值
        for (Store store : stores) {
            StockCheckStatDataVO stockCheckStatDataVO = new StockCheckStatDataVO();
            stockCheckStatDataVO.setStoreId(store.getId());
            stockCheckStatDataVO.setStoreName(store.getName());
            stockCheckStatDataVO.setStoreStatus(store.getStatus());
            List<StoreGoodsStatDataVO> goodsList = this.getGoods(store.getId());
            // 仓库商品种类总量
            stockCheckStatDataVO.setGoodsStat(goodsList.size());
            // 当前库存总量(包含待出库总量)
            long stockStat = 0;
            // 累计入库总量
            long inStockStat = 0;
            // 累计出库总量
            long outStockStat = 0;
            // 待出库总量
            long outtingStockStat = 0;
            // 当前库存总额(包含待出库总量)
            long amount = 0;
            for (StoreGoodsStatDataVO storeGoodsStatDataVO : goodsList) {
                // 仓库每个商品实际库存余量
                long temp = storeGoodsStatDataVO.getInTotal() - storeGoodsStatDataVO.getOutTotal();
                stockStat += temp;
                amount += temp * storeGoodsStatDataVO.getPrice();
                inStockStat += storeGoodsStatDataVO.getInTotal();
                outStockStat += storeGoodsStatDataVO.getOutTotal();
                outtingStockStat += storeGoodsStatDataVO.getOuttingTotal();
            }
            stockCheckStatDataVO.setStockStat(stockStat);
            stockCheckStatDataVO.setInStockStat(inStockStat);
            stockCheckStatDataVO.setOutStockStat(outStockStat);
            stockCheckStatDataVO.setOuttingStockStat(outtingStockStat);
            stockCheckStatDataVO.setAmount(amount);

            data.add(stockCheckStatDataVO);
        }
        return data;
    }
}
