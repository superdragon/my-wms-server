package com.jiaming.wms.account.bean.vo;

import com.jiaming.wms.common.entity.PageVO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class FindAccountVO extends PageVO implements Serializable {
    private static final long serialVersionUID = 6225438757692312908L;
    private String keyWord;
}
