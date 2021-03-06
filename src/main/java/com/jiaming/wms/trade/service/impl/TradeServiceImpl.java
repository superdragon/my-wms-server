package com.jiaming.wms.trade.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.common.exception.ApiException;
import com.jiaming.wms.customer.service.ICustomerService;
import com.jiaming.wms.goods.bean.vo.GoodsDataVO;
import com.jiaming.wms.goods.service.IGoodsService;
import com.jiaming.wms.goodsunit.bean.entity.GoodsBrand;
import com.jiaming.wms.goodsunit.bean.entity.GoodsPack;
import com.jiaming.wms.goodsunit.bean.entity.GoodsTaste;
import com.jiaming.wms.goodsunit.service.IGoodsBrandService;
import com.jiaming.wms.goodsunit.service.IGoodsPackService;
import com.jiaming.wms.goodsunit.service.IGoodsTasteService;
import com.jiaming.wms.mq.TopicConstants;
import com.jiaming.wms.saler.service.ISalerService;
import com.jiaming.wms.stat.bean.entity.StoreGoodsStat;
import com.jiaming.wms.stat.service.IStoreGoodsStatService;
import com.jiaming.wms.trade.bean.entity.PayType;
import com.jiaming.wms.trade.bean.entity.Trade;
import com.jiaming.wms.trade.bean.entity.TradeItem;
import com.jiaming.wms.trade.bean.entity.TradeStatus;
import com.jiaming.wms.trade.bean.vo.*;
import com.jiaming.wms.trade.mapper.TradeMapper;
import com.jiaming.wms.trade.service.ITradeItemService;
import com.jiaming.wms.trade.service.ITradeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author dragon
 */
@Slf4j
@Service
public class TradeServiceImpl extends ServiceImpl<TradeMapper, Trade>
        implements ITradeService {

    @Autowired
    ITradeItemService tradeItemService;

    @Autowired
    IStoreGoodsStatService storeGoodsStatService;

    @Autowired
    IGoodsService goodsService;

    @Autowired
    ISalerService salerService;

    @Autowired
    IGoodsBrandService goodsBrandService;

    @Autowired
    IGoodsPackService goodsPackService;

    @Autowired
    IGoodsTasteService goodsTasteService;

    @Autowired
    ICustomerService customerService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTrade(AddTradeVO addTradeVO) {
        // ??????????????????
        TradeDetailDataVO detailVO = new TradeDetailDataVO();
        List<TradeItemDetail> tradeItemDetails = new ArrayList<>();

        // ??????????????????????????????
        Trade trade = new Trade();
        // ???????????????
        String nowStr = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
        // YYYYMMDDHHMMSSSSS+??????????????????
        String customePhone = addTradeVO.getCustomePhone();
        String id = nowStr + customePhone.substring(customePhone.length() - 4, customePhone.length());
        trade.setId(id);
        BeanUtils.copyProperties(addTradeVO, trade);

        // ???????????????????????????????????????????????????
        List<TradeItem> tradeItems = new ArrayList<>();
        long tradeTotalPrice = 0;
        for (AddTradeItemVO item : addTradeVO.getGoods()) {
            GoodsDataVO goods = goodsService.getInfoById(item.getGoodsId());
            if (goods == null) {
                throw new ApiException("???????????????");
            }

            TradeItem tradeItem = new TradeItem();
            tradeItem.setGoodsId(item.getGoodsId());
            tradeItem.setGoodsNum(item.getGoodsNum());
            tradeItem.setTradeId(id);
            tradeItem.setGoodsName(item.getGoodsName());
            tradeItem.setGoodsPrice(goods.getPrice());
            tradeItem.setBrandId(goods.getBrandId());
            tradeItem.setPackId(goods.getPackId());
            tradeItem.setTasteId(goods.getTasteId());

            // ??????????????????
            long totalPrice = goods.getPrice() * item.getGoodsNum();
            tradeItem.setTotalPrice(totalPrice);

            // ????????????????????????
            tradeTotalPrice += totalPrice;

            // ?????????????????????????????????????????????????????????
            tradeItems.add(tradeItem);

            TradeItemDetail tradeItemDetail = new TradeItemDetail();
            BeanUtils.copyProperties(tradeItem, tradeItemDetail);
            GoodsBrand goodsBrand = goodsBrandService.getById(tradeItemDetail.getBrandId());
            if (goodsBrand != null) {
                tradeItemDetail.setBrandName(goodsBrand.getName());
            }
            GoodsPack goodsPack = goodsPackService.getById(tradeItemDetail.getPackId());
            if (goodsPack != null) {
                tradeItemDetail.setPackName(goodsPack.getName());
            }
            GoodsTaste goodsTaste = goodsTasteService.getById(tradeItemDetail.getTasteId());
            if (goodsTaste != null) {
                tradeItemDetail.setTasteName(goodsTaste.getName());
            }
            tradeItemDetail.setGoodsImages(goods.getGoodsImages());
            tradeItemDetails.add(tradeItemDetail);
        }
        trade.setTotalPrice(tradeTotalPrice);
        trade.setStatus(TradeStatus.PRE_PAY);
        BeanUtils.copyProperties(trade, detailVO);

        // ????????????????????????
        this.save(trade);
        // ????????????????????????
        tradeItemService.saveBatch(tradeItems);
        // ??????????????????
        detailVO.setItems(tradeItemDetails);
        detailVO.setTotalPrice(tradeTotalPrice);
        redisTemplate.opsForValue().set(detailVO.getId(), JSONUtil.toJsonStr(detailVO));
    }

    @Override
    public MyPage<Trade> pageTrade(PageTradeVO pageTradeVO) {
        IPage<Trade> page = new Page<>();
        page.setSize(pageTradeVO.getPageSize());
        page.setCurrent(pageTradeVO.getPageNum());

        QueryWrapper<Trade> wrapper = Wrappers.query();
        if (StrUtil.isNotEmpty(pageTradeVO.getId())) {
            wrapper.eq("id", pageTradeVO.getId());
        }
        if (pageTradeVO.getStatus() != null) {
            wrapper.eq("status", pageTradeVO.getStatus());
        }
        wrapper.orderByDesc("id");
        this.page(page, wrapper);

        MyPage<Trade> result = new MyPage<>();
        result.setTotalNum(page.getTotal());
        result.setPageNum(page.getPages());
        result.setPageSize(page.getSize());
        result.setPageNum(page.getCurrent());
        result.setItems(page.getRecords());
        return result;
    }

    @Override
    public void pay(String id, String moneyOrder) {
        // ??????????????????
        TradeDetailDataVO detail = this.detail(id);
        if (detail.getStatus() != TradeStatus.PRE_PAY) {
            throw new ApiException("???????????????");
        }

        Trade trade = new Trade();
        trade.setId(id);

        if (detail.getPayType() == PayType.REMIT_MONEY) {
            if (StrUtil.isEmpty(moneyOrder)) {
                throw new ApiException("?????????????????????");
            } else {
                trade.setMoneyOrder(moneyOrder);
            }
        }

        // ??????????????????
        redisTemplate.delete(id);

        // ?????????????????????????????????
        trade.setStatus(TradeStatus.PAY_SUCCESS);
        trade.setPayTime(new Date());
        this.updateById(trade);

        // TODO ????????????????????????
        // TODO ??????????????????
        // ??????????????????????????????????????????MQ
        rocketMQTemplate.asyncSend(TopicConstants.PAY_SUCCESS, JSONUtil.toJsonStr(trade), new SendCallback() {
            //???????????????????????????
            @Override
            public void onSuccess(SendResult sendResult) {
                // ???????????????????????????????????????????????????????????????????????????????????????????????????
                log.info("???????????????????????????????????? ?????????={} ????????????={}", trade.getId(), sendResult);
            }

            //???????????????????????????
            @Override
            public void onException(Throwable throwable) {
                // ????????????????????????????????????????????????????????????
                // 1. ??????log?????????????????????????????????????????????
                // 2. ??????ES??????????????????????????????????????????????????????????????????
                // 3. ???Redis?????????????????????????????????????????????????????????
                log.error("???????????????????????????????????? ?????????={} ????????????={}", trade.getId(), throwable.getLocalizedMessage());
            }
        });
    }

    @Override
    public void updateStatus(String id, Integer status) {
        // ??????????????????
        redisTemplate.delete(id);

        Trade trade = new Trade();
        trade.setId(id);
        trade.setStatus(status);
        if (status == TradeStatus.PAY_SUCCESS) {
            trade.setPayTime(new Date());
        }
        if (status == TradeStatus.SHIPPED_SUCCESS) {
            trade.setShipTime(new Date());
        }
        if (status == TradeStatus.RECEIVE_SUCCESS) {
            trade.setReceiveTime(new Date());
        }
        this.updateById(trade);
    }

    @Override
    public TradeDetailDataVO detail(String id) {
        // ??????????????????
        String v = redisTemplate.opsForValue().get(id);
        if (StrUtil.isNotEmpty(v)) {
            TradeDetailDataVO tradeDetailDataVO = JSONUtil.toBean(v, TradeDetailDataVO.class);
            return tradeDetailDataVO;
        } else { // ??????????????????
            TradeDetailDataVO tradeDetailDataVO = new TradeDetailDataVO();
            List<TradeItemDetail> tradeItemDetails = new ArrayList<>();
            // ????????????????????????
            Trade trade = this.getById(id);
            BeanUtils.copyProperties(trade, tradeDetailDataVO);
            // ??????????????????
            QueryWrapper<TradeItem> queryWrapper = Wrappers.query();
            queryWrapper.eq("trade_id", id);
            List<TradeItem> items = tradeItemService.list(queryWrapper);

            for (TradeItem item : items) {
                GoodsDataVO goods = goodsService.getInfoById(item.getGoodsId());
                if (goods == null) {
                    throw new ApiException("???????????????");
                }

                TradeItemDetail tradeItemDetail = new TradeItemDetail();
                BeanUtils.copyProperties(item, tradeItemDetail);
                tradeItemDetail.setGoodsImages(goods.getGoodsImages());
                GoodsBrand goodsBrand = goodsBrandService.getById(tradeItemDetail.getBrandId());
                if (goodsBrand != null) {
                    tradeItemDetail.setBrandName(goodsBrand.getName());
                }
                GoodsPack goodsPack = goodsPackService.getById(tradeItemDetail.getPackId());
                if (goodsPack != null) {
                    tradeItemDetail.setPackName(goodsPack.getName());
                }
                GoodsTaste goodsTaste = goodsTasteService.getById(tradeItemDetail.getTasteId());
                if (goodsTaste != null) {
                    tradeItemDetail.setTasteName(goodsTaste.getName());
                }
                tradeItemDetail.setGoodsImages(goods.getGoodsImages());
                tradeItemDetails.add(tradeItemDetail);
            }
            tradeDetailDataVO.setItems(tradeItemDetails);

            // ??????????????????
            redisTemplate.opsForValue().set(tradeDetailDataVO.getId(), JSONUtil.toJsonStr(tradeDetailDataVO));
            return tradeDetailDataVO;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void goodsOut(GoodsOutVO goodsOutVO) {
        // ?????????????????????????????????
        Trade trade = new Trade();
        trade.setId(goodsOutVO.getTradeId());
        trade.setStatus(3);
        trade.setShipTime(new Date());
        trade.setEmpId(goodsOutVO.getEmpId());
        trade.setStoreId(goodsOutVO.getStoreId());
        this.updateById(trade);
        // ????????????????????????
        QueryWrapper<TradeItem> queryWrapper = Wrappers.query();
        queryWrapper.eq("trade_id", goodsOutVO.getTradeId());
        List<TradeItem> items = tradeItemService.list(queryWrapper);
        // ????????????????????????
        for (TradeItem item : items) {
            storeGoodsStatService.plusOutTotal(goodsOutVO.getStoreId(),item.getGoodsId(), item.getGoodsNum());
        }
    }

    @Override
    public Map<Long, CheckStoreDataVO> checkStore(CheckStoreVO checkStoreVO) {
        // ?????????????????????????????????
        Map<Long, CheckStoreDataVO> map = new HashMap<>();
        List<Long> goodsIds = new ArrayList<>();
        List<CheckStoreItem> items = checkStoreVO.getItems();
        for (CheckStoreItem item : items) {
            goodsIds.add(item.getGoodsId());

            CheckStoreDataVO checkStoreDataVO = new CheckStoreDataVO();
            checkStoreDataVO.setGoodsId(item.getGoodsId());
            checkStoreDataVO.setGoodsNum(item.getGoodsNum());
            map.put(item.getGoodsId(), checkStoreDataVO);
        }
        List<StoreGoodsStat> storeGoodsStats = storeGoodsStatService.getGoodsNumByStoreId(checkStoreVO.getStoreId(), goodsIds);
        // ?????????????????????????????????????????????
        for (StoreGoodsStat storeGoodsStat : storeGoodsStats) {
            CheckStoreDataVO checkStoreDataVO = map.get(storeGoodsStat.getGoodsId());
            Long storeNum = storeGoodsStat.getInTotal() - storeGoodsStat.getOutTotal() - storeGoodsStat.getOuttingTotal();
            checkStoreDataVO.setStoreNum(storeNum);
            if (storeNum >= checkStoreDataVO.getGoodsNum()) {
                // ????????????
                checkStoreDataVO.setIsOk(true);
            } else {
                checkStoreDataVO.setIsOk(false);
            }
        }
        return map;
    }

    @Override
    public List<Trade> getByDate(String statDate) {
        String sDate = statDate + " 00:00:00";
        String eDate = statDate + " 23:59:59";
        QueryWrapper<Trade> wrapper = Wrappers.query();
        wrapper.ge("status", 1);
        wrapper.ge("pay_time", sDate);
        wrapper.le("pay_time", eDate);
        wrapper.orderByDesc("id");
        return this.list(wrapper);
    }
}
