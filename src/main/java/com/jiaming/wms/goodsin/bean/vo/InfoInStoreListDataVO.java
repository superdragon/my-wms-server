package com.jiaming.wms.goodsin.bean.vo;

import com.jiaming.wms.employee.bean.entity.Employee;
import com.jiaming.wms.goodsin.bean.entity.InStoreList;
import com.jiaming.wms.store.bean.entity.Store;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class InfoInStoreListDataVO extends InStoreList implements Serializable {
    private static final long serialVersionUID = 3506815205643875903L;
    // 仓库信息
    private Store store;
    // 员工信息
    private Employee employee;
}
