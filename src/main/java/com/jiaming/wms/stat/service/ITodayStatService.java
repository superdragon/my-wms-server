package com.jiaming.wms.stat.service;

/**
 * @author dragon
 */
public interface ITodayStatService {
    void plusTodayTransferTotal();

    Long getByRedis(String key);

    void plusTodayInStoreTotal();
}
