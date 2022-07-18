package com.jiaming.wms.stat.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaming.wms.stat.bean.constants.RedisKey;
import com.jiaming.wms.stat.bean.entity.StatTrade;
import com.jiaming.wms.stat.bean.vo.LatestStoreTradeStatDataVO;
import com.jiaming.wms.stat.bean.vo.LatestTradeStatDataVO;
import com.jiaming.wms.stat.mapper.StatTradeMapper;
import com.jiaming.wms.stat.service.IStatTradeService;
import com.jiaming.wms.store.bean.entity.Store;
import com.jiaming.wms.store.service.IStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * @author dragon
 */
@Slf4j
@Service
public class StatTradeServiceImpl extends ServiceImpl<StatTradeMapper, StatTrade> implements IStatTradeService {
    @Autowired
    IStoreService storeService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public void initData() {
        // 测试：清空上次测试数据
        String date = DateUtil.format(new Date(), "yyyyMMdd");
        QueryWrapper<StatTrade> wrapper = Wrappers.query();
        wrapper.le("stat_date", date);
        this.remove(wrapper);

        // 获取每个仓库信息
        List<Store> stores = storeService.list();

        // 随机创建近30日的数据
        log.info("》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》");
        log.info("开始随机创建近30日的交易数据");
        Date now = new Date();
        for (int i = 1; i <= 30; i++) {
            DateTime offsetDay = DateUtil.offsetDay(now, -i);
            String statDate = DateUtil.format(offsetDay, "yyyyMMdd");

            // 每日测试数据集合
            List<StatTrade> statTrades = new ArrayList<>();

            for (Store item : stores) {
                StatTrade statTrade = new StatTrade();
                statTrade.setStatDate(Integer.parseInt(statDate));
                statTrade.setStoreId(item.getId());
                // 随机4位数值的销量
                long total = Long.parseLong(RandomUtil.randomNumbers(4));
                statTrade.setTotal(total);
                // 随机6位数值的销量
                long amount = Long.parseLong(RandomUtil.randomNumbers(6));
                statTrade.setAmount(amount);

                statTrades.add(statTrade);
                log.info("{} 每个仓库统计测试数据 {}", statDate, statTrades);
            }
            this.saveBatch(statTrades);
        }
        log.info("完成随机创建近30日的交易数据");
        log.info("《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《");
    }

    @Override
    public LatestTradeStatDataVO latestStat() {
        String s = redisTemplate.opsForValue().get(RedisKey.LATEST_STAT_TRADE);
        if (StrUtil.isNotEmpty(s)) {
            return JSONUtil.toBean(s, LatestTradeStatDataVO.class);
        }
        return null;
    }

    @Override
    public void statData() {
        log.info("》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》");
        Date now = new Date();
        DateTime offsetDay = DateUtil.offsetDay(now, -30);
        String sDate = DateUtil.format(offsetDay, "yyyyMMdd");
        offsetDay = DateUtil.offsetDay(now, -1);
        String eDate = DateUtil.format(offsetDay, "yyyyMMdd");
        log.info("{} - {} 获取近30日的交易数据和交易总量", sDate, eDate);
        List<Map<String, Object>> data = this.baseMapper.statTradeData(sDate, eDate);
        LatestTradeStatDataVO statTradeDataVO = new LatestTradeStatDataVO();
        statTradeDataVO.setSDate(Integer.parseInt(sDate));
        statTradeDataVO.setEDate(Integer.parseInt(eDate));
        statTradeDataVO.setSaleAmount(new ArrayList<>());
        statTradeDataVO.setSaleTotal(new ArrayList<>());
        statTradeDataVO.setStatDates(new ArrayList<>());
        for(Map<String, Object> m : data) {
            statTradeDataVO.getStatDates().add((Long) m.get("stat_date"));
            statTradeDataVO.getSaleTotal().add((BigDecimal)m.get("sale_total"));
            statTradeDataVO.getSaleAmount().add((BigDecimal)m.get("sale_amount"));
        }

        log.info("{} - {} 获取近30日每个仓库的交易数据和交易总量", sDate, eDate);
        Map<BigInteger, Map<String, Object>> storeTradeData = this.baseMapper.statStoreTradeData(sDate, eDate);
        LatestStoreTradeStatDataVO latestStoreTradeStatDataVO = new LatestStoreTradeStatDataVO();
        latestStoreTradeStatDataVO.setSDate(Integer.parseInt(sDate));
        latestStoreTradeStatDataVO.setEDate(Integer.parseInt(eDate));
        latestStoreTradeStatDataVO.setItems(new ArrayList<>());
        Set<BigInteger> storeIds = storeTradeData.keySet();
        // 补全仓库信息
        for (BigInteger storeId : storeIds) {
            Store store = storeService.getById(storeId);
            storeTradeData.get(storeId).put("store_name", store.getName());
            latestStoreTradeStatDataVO.getItems().add(storeTradeData.get(storeId));
        }

        log.info("放入缓存中...");
        redisTemplate.opsForValue().set(RedisKey.LATEST_STAT_TRADE, JSONUtil.toJsonStr(statTradeDataVO));
        redisTemplate.opsForValue().set(RedisKey.LATEST_STAT_STORE_TRADE, JSONUtil.toJsonStr(latestStoreTradeStatDataVO));
        log.info("《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《");
    }

    @Override
    public LatestStoreTradeStatDataVO latestStroeStat() {
        String s = redisTemplate.opsForValue().get(RedisKey.LATEST_STAT_STORE_TRADE);
        if (StrUtil.isNotEmpty(s)) {
            return JSONUtil.toBean(s, LatestStoreTradeStatDataVO.class);
        }
        return null;
    }
}
