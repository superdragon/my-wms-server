package com.jiaming.wms.stat.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaming.wms.goods.bean.entity.Goods;
import com.jiaming.wms.goods.service.IGoodsService;
import com.jiaming.wms.stat.bean.constants.RedisKey;
import com.jiaming.wms.stat.bean.entity.StatGoods;
import com.jiaming.wms.stat.bean.vo.LatestGoodsTopDataVO;
import com.jiaming.wms.stat.bo.StoreGoodsInitDataBO;
import com.jiaming.wms.stat.mapper.StatGoodsMapper;
import com.jiaming.wms.stat.service.IStatGoodsService;
import com.jiaming.wms.stat.service.IStoreGoodsStatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author dragon
 */
@Slf4j
@Service
public class StatGoodsServiceImpl extends ServiceImpl<StatGoodsMapper, StatGoods> implements IStatGoodsService {
    @Autowired
    IStoreGoodsStatService storeGoodsStatService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    IGoodsService goodsService;

    @Override
    public void initData() {
        // 测试：清空上次测试数据
        String date = DateUtil.format(new Date(), "yyyyMMdd");
        QueryWrapper<StatGoods> wrapper = Wrappers.query();
        wrapper.le("stat_date", date);
        this.remove(wrapper);

        // 获取每个仓库的所有商品信息
        List<StoreGoodsInitDataBO> items = storeGoodsStatService.getAllGoods();

        // 随机创建近30日的数据
        log.info("》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》");
        log.info("开始随机创建近30日的商品统计数据");
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
            log.info("完成随机创建近30日的商品统计数据");
            log.info("《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《");
        }
    }

    @Override
    public void statData() {
        log.info("》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》");
        Date now = new Date();
        DateTime offsetDay = DateUtil.offsetDay(now, -30);
        String sDate = DateUtil.format(offsetDay, "yyyyMMdd");
        offsetDay = DateUtil.offsetDay(now, -1);
        String eDate = DateUtil.format(offsetDay, "yyyyMMdd");
        log.info("{} - {} 获取近30日的商品交易总量", sDate, eDate);
        List<Map<String, Object>> data = this.baseMapper.statGoodsData(sDate, eDate);

        log.info("放入缓存中...");
        for (Map<String, Object> m : data) {
            redisTemplate.opsForZSet().add(RedisKey.LATEST_STAT_GOODS, m.get("goods_id").toString(), ((BigDecimal)m.get("sale_amount")).longValue());
        }
        log.info("《《《《《《《《《《《《《《《《《《《《《《《《《《《《《《");
    }

    @Override
    public List<LatestGoodsTopDataVO> latestGoodsTop() {
        List<LatestGoodsTopDataVO> data = new ArrayList<>();

        Set<ZSetOperations.TypedTuple<String>> goodsIds = redisTemplate.opsForZSet().reverseRangeWithScores(RedisKey.LATEST_STAT_GOODS, 0, 4);
        for (ZSetOperations.TypedTuple<String> g : goodsIds) {
            Goods goods = goodsService.getById(Long.parseLong(g.getValue()));
            LatestGoodsTopDataVO latestGoodsTopDataVO = new LatestGoodsTopDataVO();
            latestGoodsTopDataVO.setId(goods.getId());
            latestGoodsTopDataVO.setName(goods.getName());
            latestGoodsTopDataVO.setSaleAmount(g.getScore().longValue());
            data.add(latestGoodsTopDataVO);
        }
        return data;
    }
}
