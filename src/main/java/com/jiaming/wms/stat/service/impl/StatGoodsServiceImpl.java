package com.jiaming.wms.stat.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaming.wms.stat.bean.entity.StatGoods;
import com.jiaming.wms.stat.bo.StoreGoodsInitDataBO;
import com.jiaming.wms.stat.mapper.StatGoodsMapper;
import com.jiaming.wms.stat.service.IStatGoodsService;
import com.jiaming.wms.stat.service.IStoreGoodsStatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author dragon
 */
@Slf4j
@Service
public class StatGoodsServiceImpl extends ServiceImpl<StatGoodsMapper, StatGoods> implements IStatGoodsService {
    @Autowired
    IStoreGoodsStatService storeGoodsStatService;

    @Override
    public void initData() {
        // 获取每个仓库的所有商品信息
        List<StoreGoodsInitDataBO> items = storeGoodsStatService.getAllGoods();

        // 随机创建近30日的数据
        Date now = new Date();
        for (int i = 1; i <= 30; i++) {
            DateTime offsetDay = DateUtil.offsetDay(now, -i);
            String statDate = DateUtil.format(offsetDay, "yyyyMMdd");

            // 每日测试数据集合
            List<StatGoods> statGoodsList = new ArrayList<>();

            for (StoreGoodsInitDataBO item : items) {
                StatGoods statGoods = new StatGoods();
                statGoods.setStatDate(Integer.parseInt(statDate));
                statGoods.setBrandId(item.getBrandId());
                statGoods.setGoodsId(item.getId());
                statGoods.setPackId(item.getPackId());
                statGoods.setTasteId(item.getTasteId());
                statGoods.setStoreId(item.getStoreId());
                // 随机3位数值的销量
                long total = Long.parseLong(RandomUtil.randomNumbers(3));
                statGoods.setTotal(total);
                // 销售额
                long amount = total * item.getPrice();
                statGoods.setAmount(amount);

                statGoodsList.add(statGoods);
                log.info("{} 每个仓库每种商品统计测试数据 {}", statGoods.getStatDate(), statGoods);
            }
            this.saveBatch(statGoodsList);
        }
    }
}
