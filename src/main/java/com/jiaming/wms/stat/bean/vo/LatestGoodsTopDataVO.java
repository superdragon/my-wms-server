package com.jiaming.wms.stat.bean.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class LatestGoodsTopDataVO implements Serializable {
    private static final long serialVersionUID = 733698147820780352L;
    private Long id;
    private String name;
    private Long saleAmount;
}
