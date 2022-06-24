package com.jiaming.wms.safestore.bean.vo;

import com.jiaming.wms.common.entity.PageVO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class PageSafeStoreVO extends PageVO implements Serializable {
    private static final long serialVersionUID = 7230764972697327232L;
    private String name;
    @NotNull(message = "仓库信息不正确")
    private Long storeId;
}
