package com.jiaming.wms.goods.bean.vo;

import com.jiaming.wms.common.entity.PageVO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class FilterGoodsVO extends PageVO implements Serializable {
    private static final long serialVersionUID = 8259010653873720764L;
    private String keyWord;
    private Long brandId;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer status;
}
