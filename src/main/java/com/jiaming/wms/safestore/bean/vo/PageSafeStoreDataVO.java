package com.jiaming.wms.safestore.bean.vo;

import com.jiaming.wms.safestore.bean.entity.SafeStore;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class PageSafeStoreDataVO extends SafeStore implements Serializable {
    private static final long serialVersionUID = 3363722453980564336L;
    private String goodsName;
    private String goodsCode;
    private String storeName;
}
