package com.jiaming.wms.transfer.bean.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author dragon
 */
@Data
public class TransferDetailVO implements Serializable {
    private static final long serialVersionUID = 93423982001455900L;
    private PageTransferListDataVO info;
    private List<PageTransferItemDataVO> items;
}
