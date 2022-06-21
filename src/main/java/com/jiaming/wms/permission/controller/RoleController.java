package com.jiaming.wms.permission.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jiaming.wms.common.exception.ApiException;
import com.jiaming.wms.common.response.ResultCodeEnum;
import com.jiaming.wms.common.response.ResultVO;
import com.jiaming.wms.permission.bean.entity.AccountRole;
import com.jiaming.wms.permission.bean.entity.Permission;
import com.jiaming.wms.permission.bean.entity.Role;
import com.jiaming.wms.permission.bean.vo.AddRoleVO;
import com.jiaming.wms.permission.bean.vo.RolePermissionDataVO;
import com.jiaming.wms.permission.bean.vo.UpdateRoleVO;
import com.jiaming.wms.permission.service.IAccountRoleService;
import com.jiaming.wms.permission.service.IRolePermissionService;
import com.jiaming.wms.permission.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * @author dragon
 */
@Api(tags = "角色API")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    IRoleService roleService;

    @Autowired
    IRolePermissionService rolePermissionService;

    @Autowired
    IAccountRoleService accountRoleService;

    @ApiOperation("添加角色")
    @PostMapping("/add")
    public ResultVO add(@RequestBody @Valid AddRoleVO roleVO) {
        roleService.add(roleVO);
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }

    @ApiOperation("修改角色")
    @PostMapping("/update")
    public ResultVO update(@RequestBody @Valid UpdateRoleVO roleVO) {
        roleService.update(roleVO);
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }

    @ApiOperation("获得所有角色")
    @GetMapping("/findAll")
    public ResultVO<List<Role>> findAll() {
        QueryWrapper<Role> wrapper = Wrappers.query();
        wrapper.orderByDesc("id");
        return new ResultVO<>(ResultCodeEnum.SUCCESS, roleService.list(wrapper));
    }

    @ApiOperation("获取角色和权限信息")
    @PostMapping("/getPermission")
    public ResultVO<RolePermissionDataVO> getPermission(@RequestParam Long id) {
        Role role = roleService.getById(id);
        if (role == null) {
            throw new ApiException("没有该角色");
        }
        RolePermissionDataVO dataVO = new RolePermissionDataVO();
        BeanUtils.copyProperties(role, dataVO);
        List<Permission> data = rolePermissionService.getPermissionsByRoleIds(Arrays.asList(role.getId()));
        dataVO.setPermissions(data);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, dataVO);
    }

    @ApiOperation("删除角色")
    @PostMapping("/remove")
    public ResultVO remove(@RequestParam Long id) {
        // 判断角色是否还有账户使用
        List<AccountRole> accountRoles = accountRoleService.getByRoleId(id);
        if (accountRoles != null && accountRoles.size() > 0) {
            throw new ApiException("还有账户使用该角色，无法删除");
        }
        roleService.removeRoleAndPermission(id);
        return new ResultVO(ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/getByAccountId")
    public ResultVO<List<Role>> getByAccountId(@RequestParam Long accountId) {
        List<Role> roles = roleService.getByAccountId(accountId);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, roles);
    }

    @ApiOperation("保存账户和角色关系")
    @PostMapping("/saveAccountRoles")
    public ResultVO saveAccountRoles(@RequestParam Long accountId, @RequestParam String roleIds) {
        roleService.saveAccountRoles(accountId, roleIds);
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }
}
