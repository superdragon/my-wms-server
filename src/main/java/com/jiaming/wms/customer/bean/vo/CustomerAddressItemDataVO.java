package com.jiaming.wms.customer.bean.vo;

import com.jiaming.wms.customer.bean.entity.CustomerAddress;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class CustomerAddressItemDataVO extends CustomerAddress implements Serializable {
    private static final long serialVersionUID = 1425725134152480454L;
    private String provName;
    private String cityName;
}
