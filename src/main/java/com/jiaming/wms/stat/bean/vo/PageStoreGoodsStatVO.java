package com.jiaming.wms.stat.bean.vo;

import com.jiaming.wms.common.entity.PageVO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class PageStoreGoodsStatVO extends PageVO implements Serializable {
    private static final long serialVersionUID = -8332724804812156524L;
    private Long storeId;
    private Long goodsId;
}
