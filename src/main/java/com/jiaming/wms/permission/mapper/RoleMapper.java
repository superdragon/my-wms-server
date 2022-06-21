package com.jiaming.wms.permission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaming.wms.permission.bean.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author dragon
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> getByAccountId(Long accountId);
}
