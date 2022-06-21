package com.jiaming.wms.region.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaming.wms.region.bean.entity.Region;
import com.jiaming.wms.region.mapper.RegionMapper;
import com.jiaming.wms.region.service.IRegionService;
import org.springframework.stereotype.Service;

/**
 * @author dragon
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements IRegionService {
}
