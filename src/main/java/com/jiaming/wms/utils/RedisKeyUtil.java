package com.jiaming.wms.utils;

import cn.hutool.core.date.DateUtil;
import com.jiaming.wms.stat.bean.constants.RedisKey;

import java.util.Date;

/**
 * @author dragon
 */
public class RedisKeyUtil {

    public static String getTodayTransferStat() {
        // 获取今日日期
        String now = DateUtil.format(new Date(), "yyyyMMdd");
        return RedisKey.STAT_TRANSFER_PREFIX + now;
    }

    public static String getTodayInStoreStat() {
        // 获取今日日期
        String now = DateUtil.format(new Date(), "yyyyMMdd");
        return RedisKey.STAT_INSTORE_PREFIX + now;
    }

    public static String getTodayTradeStat() {
        String now = DateUtil.format(new Date(), "yyyyMMdd");
        return RedisKey.STAT_TRADE_PREFIX + now;
    }

    public static String getTodayTradeUserStat() {
        String now = DateUtil.format(new Date(), "yyyyMMdd");
        return RedisKey.STAT_TRADE_USER_PREFIX + now;
    }

    public static String getPaySuccessNotify() {
        return RedisKey.PAY_SUCCESS_NOTIFY;
    }
}
