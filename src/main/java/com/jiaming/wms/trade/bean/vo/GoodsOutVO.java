package com.jiaming.wms.trade.bean.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class GoodsOutVO implements Serializable {
    private static final long serialVersionUID = -8184590961562260066L;
    @NotEmpty(message = "订单ID不能为空")
    private String tradeId;
    @NotNull(message = "仓库ID不能为空")
    private Long storeId;
    @NotNull(message = "员工ID不能为空")
    private Long empId;
}
