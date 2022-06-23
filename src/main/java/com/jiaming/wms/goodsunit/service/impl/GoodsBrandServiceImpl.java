package com.jiaming.wms.goodsunit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaming.wms.goodsunit.bean.entity.GoodsBrand;
import com.jiaming.wms.goodsunit.mapper.GoodsBrandMapper;
import com.jiaming.wms.goodsunit.service.IGoodsBrandService;
import org.springframework.stereotype.Service;

/**
 * @author dragon
 */
@Service
public class GoodsBrandServiceImpl extends ServiceImpl<GoodsBrandMapper, GoodsBrand> implements IGoodsBrandService {
}
