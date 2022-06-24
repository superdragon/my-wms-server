package com.jiaming.wms.store.bean.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class AddStoreVO implements Serializable {
    private static final long serialVersionUID = -5298585606334716010L;
    @NotEmpty(message = "仓库名称不正确")
    private String name;

    @NotEmpty(message = "联系人名称不正确")
    private String conName;

    @NotEmpty(message = "联系电话不正确")
    @Size(min = 11, max = 15, message = "联系电话不正确")
    private String conPhone;

    @NotNull(message = "省份ID不正确")
    private Long provId;

    @NotNull(message = "城市ID不正确")
    private Long cityId;

    @NotEmpty(message = "详细地址不正确")
    private String address;

    @NotNull(message = "安全库存不正确")
    @Range(min = 1, message = "安全库存不正确")
    private Long safeNum;

    @NotNull(message = "状态不正确")
    @Range(min = 0, max = 1, message = "状态不正确")
    private Integer status;
}
