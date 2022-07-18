package com.jiaming.wms.stat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaming.wms.stat.bean.entity.StatGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author dragon
 */
@Mapper
public interface StatGoodsMapper extends BaseMapper<StatGoods> {
    List<Map<String, Object>> statGoodsData(@Param("sDate") String sDate, @Param("eDate") String eDate);
}
