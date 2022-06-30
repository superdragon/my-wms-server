package com.jiaming.wms.employee.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.common.response.ResultCodeEnum;
import com.jiaming.wms.common.response.ResultVO;
import com.jiaming.wms.employee.bean.entity.Employee;
import com.jiaming.wms.employee.bean.vo.AddEmpVO;
import com.jiaming.wms.employee.bean.vo.PageEmpDataVO;
import com.jiaming.wms.employee.bean.vo.PageEmpVO;
import com.jiaming.wms.employee.bean.vo.UpdateEmpVO;
import com.jiaming.wms.employee.service.IEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author dragon
 */
@Api(tags = "员工API")
@RestController
@RequestMapping("/emp")
public class EmployeeController {

    @Autowired
    IEmployeeService employeeService;

    @ApiOperation("添加员工信息")
    @PostMapping("/add")
    public ResultVO add(@RequestBody @Valid AddEmpVO empVO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(empVO, employee);
        employeeService.save(employee);
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }

    @ApiOperation("更新员工信息")
    @PostMapping("/update")
    public ResultVO update(@RequestBody @Valid UpdateEmpVO empVO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(empVO, employee);
        employeeService.updateById(employee);
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }

    @ApiOperation("分页查询员工信息")
    @PostMapping("/page")
    public ResultVO<MyPage<PageEmpDataVO>> page(@RequestBody @Valid PageEmpVO empVO) {
        MyPage<PageEmpDataVO> data = employeeService.myPage(empVO);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, data);
    }

    @ApiOperation("查询某仓库的员工信息")
    @PostMapping("/list")
    public ResultVO<List<Employee>> list(@RequestParam Long storeId) {
        QueryWrapper<Employee> query = Wrappers.query();
        query.eq("store_id", storeId);
        List<Employee> employees = employeeService.list(query);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, employees);
    }
}
