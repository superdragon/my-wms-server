package com.jiaming.wms.transfer.bean.vo;

import com.jiaming.wms.goods.bean.entity.Goods;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class PageTransferItemDataVO extends Goods implements Serializable {
    private static final long serialVersionUID = 8259010653873720764L;
    private String brandName;
    private String packName;
    private String tasteName;
    private Long goodsNum;
    private Long transferItemId;
}
