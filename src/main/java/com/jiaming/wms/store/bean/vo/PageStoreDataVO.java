package com.jiaming.wms.store.bean.vo;

import com.jiaming.wms.store.bean.entity.Store;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class PageStoreDataVO extends Store implements Serializable {
    private static final long serialVersionUID = -5298585606334716010L;
    private String provName;
    private String cityName;
}
