package com.jiaming.wms.customer.controller;

import com.jiaming.wms.common.response.ResultCodeEnum;
import com.jiaming.wms.common.response.ResultVO;
import com.jiaming.wms.customer.bean.vo.CustomerAddressItemDataVO;
import com.jiaming.wms.customer.service.ICustomerAddressService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author dragon
 */
@Api(tags = "客户地址API")
@RestController
@RequestMapping("/customerAddress")
public class CustomerAddressController {

    @Autowired
    ICustomerAddressService customerAddressService;

    @PostMapping("/list")
    public ResultVO<List<CustomerAddressItemDataVO>> listByCustomerId(@RequestParam Long customerId) {
        List<CustomerAddressItemDataVO> data = customerAddressService.getAddress(customerId);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, data);
    }
}
