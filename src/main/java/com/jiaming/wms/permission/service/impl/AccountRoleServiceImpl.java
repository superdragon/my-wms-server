package com.jiaming.wms.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaming.wms.permission.bean.entity.AccountRole;
import com.jiaming.wms.permission.mapper.AccountRoleMapper;
import com.jiaming.wms.permission.service.IAccountRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dragon
 */
@Service
public class AccountRoleServiceImpl extends ServiceImpl<AccountRoleMapper, AccountRole>
        implements IAccountRoleService {
    @Override
    public List<AccountRole> getByRoleId(Long id) {
        QueryWrapper<AccountRole> wrapper = Wrappers.query();
        wrapper.eq("role_id", id);
        return this.list(wrapper);
    }
}
