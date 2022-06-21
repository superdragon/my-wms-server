package com.jiaming.wms.permission.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaming.wms.permission.bean.entity.AccountRole;
import com.jiaming.wms.permission.bean.entity.Permission;
import com.jiaming.wms.permission.bean.vo.MenuVO;
import com.jiaming.wms.permission.bean.vo.PermissionDataVO;

import java.util.List;

/**
 * @author dragon
 */
public interface IPermissionService extends IService<Permission> {
    List<Permission> findByAccountId(Long accountId);

    void removeAllById(Long id);

    /**
     * 根据账户ID获取相关角色信息
     * @param id
     * @return
     */
    List<AccountRole> getRolesByAccountId(Long id);

    /**
     * 根据账户角色ID获取相关权限信息
     * @param roles
     * @return
     */
    PermissionDataVO getPermissionsByRoleId(List<AccountRole> roles);

    List<MenuVO> findAll();
}
