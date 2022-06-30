package com.jiaming.wms.goodsin.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiaming.wms.common.entity.PageVO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dragon
 */
@Data
public class PageInStoreListVO extends PageVO implements Serializable {
    private static final long serialVersionUID = -4946630875533110588L;
    private String id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date eDate;
    private Long storeId;
}
