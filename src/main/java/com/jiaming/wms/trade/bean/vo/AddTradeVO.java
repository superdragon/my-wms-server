package com.jiaming.wms.trade.bean.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author dragon
 */
@Data
public class AddTradeVO implements Serializable {
    private static final long serialVersionUID = -700222633527676734L;
    @NotNull(message = "客户信息不能为空")
    private Long customerId;
    @NotEmpty(message = "客户电话不能为空")
    private String customePhone;
    @NotNull(message = "销售经理信息不能为空")
    private Long salerId;
    @NotEmpty(message = "收货地址不能为空")
    private String expressAddress;
    @NotEmpty(message = "收货人姓名不能为空")
    private String expressName;
    @NotEmpty(message = "收货人联系电话不能为空")
    private String expressPhone;
    @NotEmpty(message = "收货地址省份信息不能为空")
    private String expressProvId;
    @NotEmpty(message = "收货地址城市不能为空")
    private String expressCityId;
    @NotNull(message = "支付方式不正确")
    private Integer payType;
    @NotNull(message = "商品信息不正确")
    private List<AddTradeItemVO> goods;
}
