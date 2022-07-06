package com.jiaming.wms.transfer.bean.vo;

import com.jiaming.wms.goodsin.bean.vo.AddInStoreItemVO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author dragon
 */
@Data
public class AddTransferListVO implements Serializable {
    private static final long serialVersionUID = 2205392347930323862L;
    @NotNull(message = "发货仓库信息不正确")
    private Long shipStoreId;
    @NotNull(message = "发货员工信息不正确")
    private Long shipEmpId;
    @NotNull(message = "收货仓库信息不正确")
    private Long receiveStoreId;
    @NotNull(message = "商品信息不正确")
    private List<AddInStoreItemVO> goods;
}
