package com.jiaming.wms.transfer.bean.vo;

import com.jiaming.wms.transfer.bean.entity.TransferList;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class PageTransferListDataVO extends TransferList implements Serializable {
    private static final long serialVersionUID = -5534417970049387621L;
    private String shipStoreName;
    private String shipEmpName;
    private String receiveStoreName;
}
