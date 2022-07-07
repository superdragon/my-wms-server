package com.jiaming.wms.stat.service.impl;

import cn.hutool.core.util.StrUtil;
import com.jiaming.wms.stat.service.ITodayStatService;
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
}
