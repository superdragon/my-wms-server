package com.jiaming.wms.employee.bean.vo;

import com.jiaming.wms.common.entity.PageVO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class PageEmpVO extends PageVO implements Serializable {
    private static final long serialVersionUID = 7775706704715203330L;
    private String name;
}
