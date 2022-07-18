package com.jiaming.wms.customer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaming.wms.customer.bean.entity.Customer;

import java.util.List;

/**
 * @author dragon
 */
public interface ICustomerService extends IService<Customer> {
    List<Customer> getCustomers(Long salerId);
}
