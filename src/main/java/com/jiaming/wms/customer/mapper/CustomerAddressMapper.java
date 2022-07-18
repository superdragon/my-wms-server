package com.jiaming.wms.customer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaming.wms.customer.bean.entity.CustomerAddress;
import com.jiaming.wms.customer.bean.vo.CustomerAddressItemDataVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author dragon
 */
@Mapper
public interface CustomerAddressMapper extends BaseMapper<CustomerAddress> {
    List<CustomerAddressItemDataVO> listByFilter(@Param("customerId") Long customerId);
}
