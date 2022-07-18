package com.jiaming.wms.saler.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaming.wms.saler.bean.entity.Saler;
import com.jiaming.wms.saler.mapper.SalerMapper;
import com.jiaming.wms.saler.service.ISalerService;
import org.springframework.stereotype.Service;

/**
 * @author dragon
 */
@Service
public class SalerServiceImpl extends ServiceImpl<SalerMapper, Saler>
        implements ISalerService {
}
