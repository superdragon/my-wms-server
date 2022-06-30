package com.jiaming.wms.goodsin.bean.vo;

import com.jiaming.wms.common.entity.PageVO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class PageInStoreGoodsVO extends PageVO implements Serializable {
    private static final long serialVersionUID = 8259010653873720764L;
    @NotEmpty(message = "入库单号不能为空")
    private String listId;
}
