package com.jiaming.wms.safestore.bean.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class UpdateSafeStoreVO implements Serializable {
    private static final long serialVersionUID = -8603053414012829539L;

    @NotNull(message = "安全库存ID不能为空")
    private Long id;

    private Long storeId;

    private Long goodsId;

    @Range(min = 1, message = "安全库存不正确")
    private Long safeNum;
}
