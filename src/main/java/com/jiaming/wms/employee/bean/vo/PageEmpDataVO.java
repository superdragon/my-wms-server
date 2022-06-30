package com.jiaming.wms.employee.bean.vo;

import com.jiaming.wms.employee.bean.entity.Employee;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class PageEmpDataVO extends Employee implements Serializable {
    private static final long serialVersionUID = -6178958564220362788L;
    private String storeName;
}
