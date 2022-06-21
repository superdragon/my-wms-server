package com.jiaming.wms.permission.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jiaming.wms.common.exception.ApiException;
import com.jiaming.wms.common.response.ResultCodeEnum;
import com.jiaming.wms.common.response.ResultVO;
import com.jiaming.wms.permission.bean.entity.Permission;
import com.jiaming.wms.permission.bean.entity.RolePermission;
import com.jiaming.wms.permission.bean.vo.AddPermissionVO;
import com.jiaming.wms.permission.bean.vo.MenuVO;
import com.jiaming.wms.permission.bean.vo.UpdatePermissionVO;
import com.jiaming.wms.permission.service.IPermissionService;
import com.jiaming.wms.permission.service.IRolePermissionService;
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
@Api(tags = "权限API")
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    IPermissionService permissionService;

    @Autowired
    IRolePermissionService rolePermissionService;

    @ApiOperation("添加权限")
    @PostMapping("/add")
    public ResultVO add(@RequestBody @Valid AddPermissionVO permissionVO) {
        Permission sysPermission = new Permission();
        BeanUtils.copyProperties(permissionVO, sysPermission);
        permissionService.save(sysPermission);
        return new ResultVO(ResultCodeEnum.SUCCESS);
    }

    @ApiOperation("更新权限")
    @PostMapping("/update")
    public ResultVO add(@RequestBody @Valid UpdatePermissionVO permissionVO) {
        Permission sysPermission = new Permission();
        BeanUtils.copyProperties(permissionVO, sysPermission);
        permissionService.updateById(sysPermission);
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }

    @ApiOperation("获得权限树")
    @GetMapping("/findAll")
    public ResultVO<List<MenuVO>> findAll() {
        List<MenuVO> data = permissionService.findAll();
        return new ResultVO<>(ResultCodeEnum.SUCCESS, data);
    }

    @ApiOperation("获取下级权限")
    @PostMapping("/getByPid")
    public ResultVO<List<Permission>> findByPid(@RequestParam Long pid) {
        QueryWrapper<Permission> query = Wrappers.query();
        query.eq("pid", pid).orderByAsc("order_no");
        List<Permission> permissions = permissionService.list(query);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, permissions);
    }

    @PostMapping("/findByAccountId")
    public ResultVO<List<Permission>> findByAccountId(@RequestParam Long accountId) {
        List<Permission> permissions = permissionService.findByAccountId(accountId);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, permissions);
    }

    @ApiOperation("删除权限")
    @PostMapping ("/remove")
    public ResultVO remove(@RequestParam Long id) {
        // 查询是否有角色关联被删除的权限
        List<RolePermission> data = rolePermissionService.getByPermissionId(id);
        if (data == null || data.size() > 0) {
            throw new ApiException("还有角色使用该权限资源，无法删除");
        }
        // 删除级联权限
        permissionService.removeAllById(id);
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }
}
