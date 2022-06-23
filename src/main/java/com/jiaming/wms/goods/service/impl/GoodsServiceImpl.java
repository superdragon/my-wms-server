package com.jiaming.wms.goods.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaming.wms.common.constants.RedisKey;
import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.goods.bean.entity.Goods;
import com.jiaming.wms.goods.bean.entity.GoodsImages;
import com.jiaming.wms.goods.bean.vo.*;
import com.jiaming.wms.goods.mapper.GoodsMapper;
import com.jiaming.wms.goods.service.IGoodsImagesService;
import com.jiaming.wms.goods.service.IGoodsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dragon
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    IGoodsImagesService goodsImagesService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(AddGoodsVO goodsVO) {
        // 保存商品信息
        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsVO, goods);
        this.save(goods);
        // 保存商品图片
        List<GoodsImages> goodsImagesList = new ArrayList<>();
        // 组装保存商品图片信息的对象
        for (String imageUrl : goodsVO.getImageUrls()) {
            // 创建需要保持记录的对象
            GoodsImages goodsImages = new GoodsImages();
            goodsImages.setImageUrl(imageUrl);
            goodsImages.setGoodsId(goods.getId());
            goodsImagesList.add(goodsImages);
        }
        // 批量保存商品图片
        goodsImagesService.saveBatch(goodsImagesList);
    }

    @Override
    public MyPage<FilterGoodsDataVO> myPage(FilterGoodsVO goodsVO) {
        // 创建MP分页对象
        IPage<Goods> page = new Page<>();
        page.setCurrent(goodsVO.getPageNum());
        page.setSize(goodsVO.getPageSize());

        this.baseMapper.myPage(page, goodsVO);

        List<FilterGoodsDataVO> data = new ArrayList<>();
        // 获取商品对应的商品图片数据
        for (Goods goods : page.getRecords()) {
            QueryWrapper<GoodsImages> query = Wrappers.query();
            query.eq("goods_id", goods.getId());
            List<GoodsImages> goodsImages = goodsImagesService.list(query);
            FilterGoodsDataVO filterGoodsDataVO = new FilterGoodsDataVO();
            BeanUtils.copyProperties(goods, filterGoodsDataVO);
            filterGoodsDataVO.setImages(goodsImages);
            data.add(filterGoodsDataVO);
        }

        // 封装自己的分页对象
        MyPage<FilterGoodsDataVO> myPage = new MyPage<>();
        myPage.setTotalPage(page.getPages());
        myPage.setPageSize(page.getSize());
        myPage.setPageNum(page.getCurrent());
        myPage.setItems(data);
        myPage.setTotalNum(page.getTotal());
        return myPage;
    }

    @Override
    public GoodsDataVO getInfoById(Long id) {
        // 判断缓存中是否有商品基本信息
        String key = RedisKey.GOODS_PREFIX + id.toString();
        String goodsStr = redisTemplate.opsForValue().get(key);
        Goods goods = null;
        if (StrUtil.isNotEmpty(goodsStr)) {
            // 如果缓存中有商品信息，直接使用，不再从DB中获取
            goods = JSONUtil.toBean(goodsStr, Goods.class);
        } else {
            // 如果缓存中没有商品信息，则从DB中获取，并同时保存在缓存中一份
            goods = this.getById(id);
            if (goods != null) {
                redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(goods));
            }
        }
        // 如果没有找到对应的商品，返回空
        if (goods == null) {
            return null;
        }
        // 通过商品ID获取商品的所有图片
        QueryWrapper<GoodsImages> query = Wrappers.query();
        query.eq("goods_id", id);
        List<GoodsImages> goodsImages = goodsImagesService.list(query);
        // 封装VO数据对象
        GoodsDataVO goodsDataVO = new GoodsDataVO();
        BeanUtils.copyProperties(goods, goodsDataVO);
        goodsDataVO.setGoodsImages(goodsImages);
        return goodsDataVO;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UpdateGoodsVO goodsVO) {
        // 更新商品基本信息
        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsVO, goods);
        goodsMapper.updateById(goods);

        // 判断是否需要更新商品图片
        if (goodsVO.getImageUrls().size() > 0) {
            // 清除以前的商品图片
            QueryWrapper<GoodsImages> delWrapper = Wrappers.query();
            delWrapper.eq("goods_id", goods.getId());
            goodsImagesService.remove(delWrapper);
            // 添加本次提交的商品图片
            // 组装保存商品图片信息的对象
            List<GoodsImages> goodsImagesList = new ArrayList<>();
            for (String imageUrl : goodsVO.getImageUrls()) {
                // 创建需要保持记录的对象
                GoodsImages goodsImages = new GoodsImages();
                goodsImages.setImageUrl(imageUrl);
                goodsImages.setGoodsId(goods.getId());
                goodsImagesList.add(goodsImages);
            }
            // 批量保存商品图片
            goodsImagesService.saveBatch(goodsImagesList);
        }

        // 删除缓存
        String key = RedisKey.GOODS_PREFIX + goodsVO.getId().toString();
        redisTemplate.delete(key);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(List<Long> ids, Integer status) {
        // 批量更新多个商品的状态
        UpdateWrapper<Goods> updateWrapper = Wrappers.update();
        updateWrapper.in("id", ids);
        updateWrapper.set("status", status);
        this.update(updateWrapper);

        // 删除缓存中对应商品
        for (Long id : ids) {
            String key = RedisKey.GOODS_PREFIX + id.toString();
            redisTemplate.delete(key);
        }
    }
}
