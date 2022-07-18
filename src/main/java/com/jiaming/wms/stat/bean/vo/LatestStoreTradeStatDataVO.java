package com.jiaming.wms.stat.bean.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author dragon
 */
@Data
public class LatestStoreTradeStatDataVO implements Serializable {
    private static final long serialVersionUID = -4521065757492186400L;
    private Integer sDate;
    private Integer eDate;
    private List<Map<String, Object>> items;
}
