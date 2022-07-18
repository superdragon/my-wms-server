package com.jiaming.wms.customer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaming.wms.customer.bean.entity.CustomerAddress;
import com.jiaming.wms.customer.bean.vo.CustomerAddressItemDataVO;
import com.jiaming.wms.customer.mapper.CustomerAddressMapper;
import com.jiaming.wms.customer.service.ICustomerAddressService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dragon
 */
@Service
public class CustomerAddressServiceImpl extends ServiceImpl<CustomerAddressMapper, CustomerAddress>
        implements ICustomerAddressService {
    @Override
    public List<CustomerAddressItemDataVO> getAddress(Long customerId) {
        return this.baseMapper.listByFilter(customerId);
    }
}
