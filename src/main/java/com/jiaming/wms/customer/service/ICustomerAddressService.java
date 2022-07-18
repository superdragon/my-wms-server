package com.jiaming.wms.customer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaming.wms.customer.bean.entity.CustomerAddress;
import com.jiaming.wms.customer.bean.vo.CustomerAddressItemDataVO;

import java.util.List;

/**
 * @author dragon
 */
public interface ICustomerAddressService extends IService<CustomerAddress> {
    List<CustomerAddressItemDataVO> getAddress(Long customerId);
}
