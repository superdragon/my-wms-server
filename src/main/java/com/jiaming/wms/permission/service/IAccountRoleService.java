package com.jiaming.wms.permission.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaming.wms.permission.bean.entity.AccountRole;

import java.util.List;

/**
 * @author dragon
 */
public interface IAccountRoleService extends IService<AccountRole> {
    List<AccountRole> getByRoleId(Long id);
}
