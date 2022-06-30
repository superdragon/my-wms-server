package com.jiaming.wms.employee.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.employee.bean.entity.Employee;
import com.jiaming.wms.employee.bean.vo.PageEmpDataVO;
import com.jiaming.wms.employee.bean.vo.PageEmpVO;

/**
 * @author dragon
 */
public interface IEmployeeService extends IService<Employee> {
    MyPage<PageEmpDataVO> myPage(PageEmpVO empVO);
}
