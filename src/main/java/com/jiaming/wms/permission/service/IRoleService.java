package com.jiaming.wms.permission.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaming.wms.permission.bean.entity.Role;
import com.jiaming.wms.permission.bean.vo.AddRoleVO;
import com.jiaming.wms.permission.bean.vo.UpdateRoleVO;

import java.util.List;

/**
 * @author dragon
 */
public interface IRoleService extends IService<Role> {
    void add(AddRoleVO roleVO);

    void update(UpdateRoleVO roleVO);

    void removeRoleAndPermission(Long id);

    List<Role> getByAccountId(Long accountId);

    void saveAccountRoles(Long accountId, String roleIds);
}
