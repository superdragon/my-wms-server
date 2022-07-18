package com.jiaming.wms.customer.controller;

import com.jiaming.wms.common.response.ResultCodeEnum;
import com.jiaming.wms.common.response.ResultVO;
import com.jiaming.wms.customer.bean.entity.Customer;
import com.jiaming.wms.customer.service.ICustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author dragon
 */
@Api(tags = "客户API")
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    ICustomerService customerService;

    @ApiOperation("分页获取客户信息")
    @PostMapping("/list")
    public ResultVO<List<Customer>> list(@RequestParam Long salerId) {
        List<Customer> data = customerService.getCustomers(salerId);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, data);
    }

    @ApiOperation("获取客户信息")
    @PostMapping("/get")
    public ResultVO<Customer> get(@RequestParam Long id) {
        return new ResultVO<>(ResultCodeEnum.SUCCESS, customerService.getById(id));
    }
}
