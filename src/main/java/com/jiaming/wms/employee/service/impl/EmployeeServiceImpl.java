package com.jiaming.wms.employee.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.employee.bean.entity.Employee;
import com.jiaming.wms.employee.bean.vo.PageEmpDataVO;
import com.jiaming.wms.employee.bean.vo.PageEmpVO;
import com.jiaming.wms.employee.mapper.EmployeeMapper;
import com.jiaming.wms.employee.service.IEmployeeService;
import org.springframework.stereotype.Service;

/**
 * @author dragon
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {
    @Override
    public MyPage<PageEmpDataVO> myPage(PageEmpVO empVO) {
        IPage<PageEmpDataVO> page = new Page<>();
        page.setCurrent(empVO.getPageNum());
        page.setSize(empVO.getPageSize());

        this.baseMapper.myPage(page, empVO);

        MyPage<PageEmpDataVO> result = new MyPage<>();
        result.setTotalNum(page.getTotal());
        result.setPageNum(page.getPages());
        result.setPageSize(page.getSize());
        result.setPageNum(page.getCurrent());
        result.setItems(page.getRecords());
        return result;
    }
}
