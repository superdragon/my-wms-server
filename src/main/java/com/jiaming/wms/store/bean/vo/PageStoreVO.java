package com.jiaming.wms.store.bean.vo;

import com.jiaming.wms.common.entity.PageVO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class PageStoreVO extends PageVO implements Serializable {
    private static final long serialVersionUID = -5298585606334716010L;
    private String name;
}
