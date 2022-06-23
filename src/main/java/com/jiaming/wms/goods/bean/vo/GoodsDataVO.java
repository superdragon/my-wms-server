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
public class GoodsDataVO extends Goods implements Serializable {
    private static final long serialVersionUID = 2614350141945444759L;
    private List<GoodsImages> goodsImages;
}
