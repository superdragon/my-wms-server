package com.jiaming.wms.trade.bean.vo;

import com.jiaming.wms.common.entity.PageVO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class PageTradeVO extends PageVO implements Serializable {
    private static final long serialVersionUID = -7338011818716660444L;
    private String id;
    private Integer status;
}
