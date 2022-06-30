package com.jiaming.wms.goodsin.bean.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author dragon
 */
@Data
public class AddInStoreListVO implements Serializable {
    private static final long serialVersionUID = -3716724173322614200L;
    @NotNull(message = "仓库信息不正确")
    private Long storeId;
    @NotNull(message = "员工信息不正确")
    private Long employeeId;
    @NotNull(message = "商品信息不正确")
    private List<AddInStoreItemVO> goods;
}
