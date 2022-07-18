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
        // 缓存订单信息
        TradeDetailDataVO detailVO = new TradeDetailDataVO();
        List<TradeItemDetail> tradeItemDetails = new ArrayList<>();

        // 创建订货订单基本信息
        Trade trade = new Trade();
        // 生成订单号
        String nowStr = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
        // YYYYMMDDHHMMSSSSS+手机号后四位
        String customePhone = addTradeVO.getCustomePhone();
        String id = nowStr + customePhone.substring(customePhone.length() - 4, customePhone.length());
        trade.setId(id);
        BeanUtils.copyProperties(addTradeVO, trade);

        // 创建订单商品明细，以及计算订单总额
        List<TradeItem> tradeItems = new ArrayList<>();
        long tradeTotalPrice = 0;
        for (AddTradeItemVO item : addTradeVO.getGoods()) {
            GoodsDataVO goods = goodsService.getInfoById(item.getGoodsId());
            if (goods == null) {
                throw new ApiException("商品不存在");
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

            // 单个商品总价
            long totalPrice = goods.getPrice() * item.getGoodsNum();
            tradeItem.setTotalPrice(totalPrice);

            // 累计单个商品总额
            tradeTotalPrice += totalPrice;

            // 保存商品明细到集合中，后续进行批量添加
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

        // 保存订单基本信息
        this.save(trade);
        // 保存订单明细信息
        tradeItemService.saveBatch(tradeItems);
        // 缓存订单信息
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
        // 获取订单信息
        TradeDetailDataVO detail = this.detail(id);
        if (detail.getStatus() != TradeStatus.PRE_PAY) {
            throw new ApiException("订单已支付");
        }

        Trade trade = new Trade();
        trade.setId(id);

        if (detail.getPayType() == PayType.REMIT_MONEY) {
            if (StrUtil.isEmpty(moneyOrder)) {
                throw new ApiException("汇款单不能为空");
            } else {
                trade.setMoneyOrder(moneyOrder);
            }
        }

        // 删除缓存信息
        redisTemplate.delete(id);

        // 更新订单状态为支付状态
        trade.setStatus(TradeStatus.PAY_SUCCESS);
        trade.setPayTime(new Date());
        this.updateById(trade);

        // TODO 给销售经理发短信
        // TODO 实时统计业务
        // 异步发送订单支付成功的消息到MQ
        rocketMQTemplate.asyncSend(TopicConstants.PAY_SUCCESS, JSONUtil.toJsonStr(trade), new SendCallback() {
            //消息发送成功的回调
            @Override
            public void onSuccess(SendResult sendResult) {
                // 如果有业务是必须在消息投递成功后处理，那么就必须放在这个方法里面写
                log.info("客户采购订单消息发送成功 订单号={} 发送结果={}", trade.getId(), sendResult);
            }

            //消息发送失败的回调
            @Override
            public void onException(Throwable throwable) {
                // 如果消息发送失败，如何追溯这条失败的消息
                // 1. 使用log记录到文件中。缺点：查询不方便
                // 2. 放到ES，推荐这一种，保证既方便查询又不容易丢失数据
                // 3. 放Redis，保证方便查询，但是不能保证数据不丢失
                log.error("客户采购订单消息发送失败 订单号={} 错误原因={}", trade.getId(), throwable.getLocalizedMessage());
            }
        });
    }

    @Override
    public void updateStatus(String id, Integer status) {
        // 删除缓存信息
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
        // 从缓存中查询
        String v = redisTemplate.opsForValue().get(id);
        if (StrUtil.isNotEmpty(v)) {
            TradeDetailDataVO tradeDetailDataVO = JSONUtil.toBean(v, TradeDetailDataVO.class);
            return tradeDetailDataVO;
        } else { // 缓存中无数据
            TradeDetailDataVO tradeDetailDataVO = new TradeDetailDataVO();
            List<TradeItemDetail> tradeItemDetails = new ArrayList<>();
            // 获取订单基本信息
            Trade trade = this.getById(id);
            BeanUtils.copyProperties(trade, tradeDetailDataVO);
            // 获取订单明细
            QueryWrapper<TradeItem> queryWrapper = Wrappers.query();
            queryWrapper.eq("trade_id", id);
            List<TradeItem> items = tradeItemService.list(queryWrapper);

            for (TradeItem item : items) {
                GoodsDataVO goods = goodsService.getInfoById(item.getGoodsId());
                if (goods == null) {
                    throw new ApiException("商品不存在");
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

            // 缓存订单信息
            redisTemplate.opsForValue().set(tradeDetailDataVO.getId(), JSONUtil.toJsonStr(tradeDetailDataVO));
            return tradeDetailDataVO;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void goodsOut(GoodsOutVO goodsOutVO) {
        // 更改订单状态为发货状态
        Trade trade = new Trade();
        trade.setId(goodsOutVO.getTradeId());
        trade.setStatus(3);
        trade.setShipTime(new Date());
        trade.setEmpId(goodsOutVO.getEmpId());
        trade.setStoreId(goodsOutVO.getStoreId());
        this.updateById(trade);
        // 获取订单商品数量
        QueryWrapper<TradeItem> queryWrapper = Wrappers.query();
        queryWrapper.eq("trade_id", goodsOutVO.getTradeId());
        List<TradeItem> items = tradeItemService.list(queryWrapper);
        // 增加商品出库数量
        for (TradeItem item : items) {
            storeGoodsStatService.plusOutTotal(goodsOutVO.getStoreId(),item.getGoodsId(), item.getGoodsNum());
        }
    }

    @Override
    public Map<Long, CheckStoreDataVO> checkStore(CheckStoreVO checkStoreVO) {
        // 获取仓库中对应商品数量
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
        // 验证仓库中商品是否满足发货数量
        for (StoreGoodsStat storeGoodsStat : storeGoodsStats) {
            CheckStoreDataVO checkStoreDataVO = map.get(storeGoodsStat.getGoodsId());
            Long storeNum = storeGoodsStat.getInTotal() - storeGoodsStat.getOutTotal() - storeGoodsStat.getOuttingTotal();
            checkStoreDataVO.setStoreNum(storeNum);
            if (storeNum >= checkStoreDataVO.getGoodsNum()) {
                // 数量足够
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
