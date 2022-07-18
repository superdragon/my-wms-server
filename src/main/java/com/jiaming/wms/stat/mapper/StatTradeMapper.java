package com.jiaming.wms.stat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaming.wms.stat.bean.entity.StatTrade;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @author dragon
 */
@Mapper
public interface StatTradeMapper extends BaseMapper<StatTrade> {
    List<Map<String, Object>> statTradeData(@Param("sDate") String sDate, @Param("eDate")String eDate);

    @MapKey("store_id")
    Map<BigInteger, Map<String, Object>> statStoreTradeData(@Param("sDate") String sDate, @Param("eDate")String eDate);

}
