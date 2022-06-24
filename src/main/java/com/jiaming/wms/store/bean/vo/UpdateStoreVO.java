package com.jiaming.wms.store.bean.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class UpdateStoreVO implements Serializable {
    private static final long serialVersionUID = -5298585606334716010L;
    @NotNull(message = "仓库ID不正确")
    private Long id;

    private String name;

    private String conName;

    @Size(min = 11, max = 15, message = "联系电话不正确")
    private String conPhone;

    @Range(min = 1, message = "安全库存不正确")
    private Long safeNum;

    private Long provId;

    private Long cityId;

    private String address;

    @Range(min = 0, max = 1, message = "状态不正确")
    private Integer status;
}
