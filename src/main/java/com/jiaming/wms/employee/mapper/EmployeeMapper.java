package com.jiaming.wms.employee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jiaming.wms.employee.bean.entity.Employee;
import com.jiaming.wms.employee.bean.vo.PageEmpDataVO;
import com.jiaming.wms.employee.bean.vo.PageEmpVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author dragon
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
    IPage<PageEmpDataVO> myPage(IPage<?> page, @Param("filter") PageEmpVO empVO);
}
