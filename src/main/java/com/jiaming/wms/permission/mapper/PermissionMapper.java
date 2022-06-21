package com.jiaming.wms.permission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaming.wms.permission.bean.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author dragon
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    List<Permission> findByAccountId(Long accountId);
}
