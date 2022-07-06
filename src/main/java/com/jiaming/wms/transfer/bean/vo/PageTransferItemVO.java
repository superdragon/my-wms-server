package com.jiaming.wms.transfer.bean.vo;

import com.jiaming.wms.common.entity.PageVO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class PageTransferItemVO extends PageVO implements Serializable {
    private static final long serialVersionUID = -4946630875533110588L;
    private String id;
}
