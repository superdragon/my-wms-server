package com.jiaming.wms.safestore.bean.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class AddSafeStoreVO implements Serializable {
    private static final long serialVersionUID = -8603053414012829539L;

    @NotNull(message = "仓库信息不正确")
    private Long storeId;

    @NotNull(message = "商品信息不正确")
    private Long goodsId;

    @NotNull(message = "安全库存不正确")
    @Range(min = 1, message = "安全库存不正确")
    private Long safeNum;
}
