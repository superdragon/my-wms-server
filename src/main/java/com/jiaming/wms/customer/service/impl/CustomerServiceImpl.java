package com.jiaming.wms.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaming.wms.customer.bean.entity.Customer;
import com.jiaming.wms.customer.mapper.CustomerMapper;
import com.jiaming.wms.customer.service.ICustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dragon
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer>
        implements ICustomerService {

    @Override
    public List<Customer> getCustomers(Long salerId) {
        QueryWrapper<Customer> wrapper = Wrappers.query();
        wrapper.eq("saler_id", salerId);
        return this.list(wrapper);
    }
}
