package com.jiaming.wms.stat.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class TodayTradeStatBO implements Serializable {
    private static final long serialVersionUID = -7992696917004735669L;
    private Long amount;
    private Long total;
}
