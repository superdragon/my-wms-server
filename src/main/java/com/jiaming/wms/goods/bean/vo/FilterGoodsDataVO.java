package com.jiaming.wms.goods.bean.vo;

import com.jiaming.wms.goods.bean.entity.Goods;
import com.jiaming.wms.goods.bean.entity.GoodsImages;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author dragon
 */
@Data
public class FilterGoodsDataVO extends Goods implements Serializable {
    private static final long serialVersionUID = 8259010653873720764L;
    private String brandName;
    private String packName;
    private String tasteName;
    private List<GoodsImages> images;
}
