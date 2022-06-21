package com.jiaming.wms.permission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaming.wms.permission.bean.entity.Permission;
import com.jiaming.wms.permission.bean.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author dragon
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
//    List<Permission> detailByRoleId(Long id);

    List<Permission> getPermissionsByRoleIds(@Param("roleIds") List<Long> roleIds);
}
