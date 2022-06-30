package com.jiaming.wms.goodsin.bean.vo;

import com.jiaming.wms.goodsin.bean.entity.InStoreList;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class PageInStoreListDataVO extends InStoreList implements Serializable {
    private static final long serialVersionUID = -5534417970049387621L;
    private String storeName;
    private String empName;
}
