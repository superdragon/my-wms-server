package com.jiaming.wms.stat.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.jiaming.wms.stat.bo.TodayTradeStatBO;
import com.jiaming.wms.stat.service.ITodayStatService;
import com.jiaming.wms.trade.bean.vo.TradeDetailDataVO;
import com.jiaming.wms.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author dragon
 */
@Service
public class TodayStatServiceImpl implements ITodayStatService {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public void plusTodayTransferTotal() {
        String key = RedisKeyUtil.getTodayTransferStat();
        redisTemplate.opsForValue().increment(key);
        // 设置24小时过期
        redisTemplate.expire(key, 24, TimeUnit.HOURS);
    }

    @Override
    public Long getByRedis(String key) {
        String s = redisTemplate.opsForValue().get(key);
        if (StrUtil.isNotEmpty(s)) {
            return Long.parseLong(s);
        }
        return 0L;
    }

    @Override
    public void plusTodayInStoreTotal() {
        String key = RedisKeyUtil.getTodayInStoreStat();
        redisTemplate.opsForValue().increment(key);
        // 设置24小时过期
        redisTemplate.expire(key, 24, TimeUnit.HOURS);
    }

    @Override
    public void pushNotifyPaySuccessInfo(TradeDetailDataVO detail) {
        if (detail != null) {
            String key = RedisKeyUtil.getPaySuccessNotify();
            redisTemplate.opsForList().leftPush(key, JSONUtil.toJsonStr(detail));
        }
    }

    @Override
    public TradeDetailDataVO getLatestNotify() {
        String key = RedisKeyUtil.getPaySuccessNotify();
        String s = redisTemplate.opsForList().rightPop(key);
        if (StrUtil.isNotEmpty(s)) {
            return JSONUtil.toBean(s, TradeDetailDataVO.class);
        }
        return null;
    }

    @Override
    public void plusTodayTradeTotal(TradeDetailDataVO tradeDetail) {
        if (tradeDetail != null) {
            String key = RedisKeyUtil.getTodayTradeStat();
            // 增加总额
            redisTemplate.opsForHash().increment(key, "amount", tradeDetail.getTotalPrice());
            // 增加总单量
            redisTemplate.opsForHash().increment(key, "total", 1);
            // 设置24小时过期
            redisTemplate.expire(key, 24, TimeUnit.HOURS);

            String userStatKey = RedisKeyUtil.getTodayTradeUserStat();
            // 添加客户ID
            redisTemplate.opsForSet().add(userStatKey, tradeDetail.getCustomerId().toString());
            // 设置24小时过期
            redisTemplate.expire(userStatKey, 24, TimeUnit.HOURS);
        }
    }

    @Override
    public TodayTradeStatBO getTodayTradeStat(String tradeStatkey) {
        String key = RedisKeyUtil.getTodayTradeStat();
        Object amount = redisTemplate.opsForHash().get(key, "amount");
        Object total = redisTemplate.opsForHash().get(key, "total");

        TodayTradeStatBO tradeStatBO = new TodayTradeStatBO();
        if (amount != null) {
            tradeStatBO.setAmount(Long.parseLong((String)amount));
        }
        if (total != null) {
            tradeStatBO.setTotal(Long.parseLong((String)total));
        }
        return tradeStatBO;
    }

    @Override
    public long getTodayTradeUserStat(String userStatKey) {
        String key = RedisKeyUtil.getTodayTradeUserStat();
        return redisTemplate.opsForSet().size(key);
    }
}
