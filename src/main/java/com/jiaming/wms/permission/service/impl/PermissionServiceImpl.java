package com.jiaming.wms.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaming.wms.permission.bean.entity.AccountRole;
import com.jiaming.wms.permission.bean.entity.Permission;
import com.jiaming.wms.permission.bean.vo.MenuNodeVO;
import com.jiaming.wms.permission.bean.vo.MenuVO;
import com.jiaming.wms.permission.bean.vo.PermissionDataVO;
import com.jiaming.wms.permission.mapper.PermissionMapper;
import com.jiaming.wms.permission.service.IAccountRoleService;
import com.jiaming.wms.permission.service.IPermissionService;
import com.jiaming.wms.permission.service.IRolePermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author dragon
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
        implements IPermissionService {

    @Autowired
    IAccountRoleService accountRoleService;

    @Autowired
    IRolePermissionService rolePermissionService;

    @Override
    public List<Permission> findByAccountId(Long accountId) {
        return this.baseMapper.findByAccountId(accountId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeAllById(Long id) {
        // 查找权限信息
        Permission permission = this.getById(id);
        Integer levelId = permission.getLevelId();
        if (levelId == 1) { // 一级权限
            // 获取二级权限
            QueryWrapper<Permission> wrapper = Wrappers.query();
            wrapper.eq("pid", id);
            List<Permission> twoPermissions = this.list(wrapper);
            for (Permission twoPermission : twoPermissions) {
                // 删除二级权限
                this.removeById(twoPermission.getId());
                QueryWrapper<Permission> threeWrapper = Wrappers.query();
                threeWrapper.eq("pid", twoPermission.getId());
                // 删除三级权限
                this.remove(threeWrapper);
            }
        }
        if (levelId == 2) { // 二级权限
            QueryWrapper<Permission> threeWrapper = Wrappers.query();
            threeWrapper.eq("pid", id);
            // 删除三级权限
            this.remove(threeWrapper);
        }
        this.removeById(id);
    }

    @Override
    public List<AccountRole> getRolesByAccountId(Long id) {
        QueryWrapper<AccountRole> query = Wrappers.query();
        query.eq("account_id", id);
        return accountRoleService.list(query);
    }

    @Override
    public PermissionDataVO getPermissionsByRoleId(List<AccountRole> roles) {
        // 获取与角色相关的权限信息
        List<Long> roleIds = new ArrayList<>();
        for (AccountRole accountRole : roles) {
            roleIds.add(accountRole.getRoleId());
        }
        List<Permission> permissions = rolePermissionService.getPermissionsByRoleIds(roleIds);

        // 获取权限层级关系的集合
        return this.permissionWrapper(permissions);
    }

    @Override
    public List<MenuVO> findAll() {
        QueryWrapper<Permission> query = Wrappers.query();
        query.orderByAsc("order_no");
        List<Permission> permissions = this.list(query);
        // 获取权限层级关系的集合
        PermissionDataVO permissionDataVO = this.permissionWrapper(permissions);
        return permissionDataVO.getItems();
    }

    /**
     *
     * @param permissions 没有权限层级的集合
     * @return 有权限层级的集合
     */
    public PermissionDataVO permissionWrapper(List<Permission> permissions) {
        PermissionDataVO permissionWarpper = new PermissionDataVO();
        permissionWarpper.setPaths(new ArrayList<>());
        permissionWarpper.setBtns(new HashSet<>());

        // 定义出响应数据结构
        List<MenuVO> permissionVOList = new ArrayList<>();
        // 分析出一级菜单，同时分析出二级菜单，三级按钮
        // key=pid value=与pid对应的二级菜单集合
        Map<Long, List<MenuNodeVO>> twoMap = new HashMap<>();
        // key=pid value=与pid对应的三级按钮集合
        Map<Long, List<Permission>> threeMap = new HashMap<>();
        for (Permission permission : permissions) {
            if (permission.getLevelId() == 1) {
                MenuVO permissionVO = new MenuVO();
                BeanUtils.copyProperties(permission, permissionVO);
                // 把一级菜单中的children属性初始化为一个空集合，避免在后面往这个集合中添加二级菜单报空指针
                permissionVO.setChildren(new ArrayList<>());
                // 把一级菜单对象添加到响应数据结构对象中
                permissionVOList.add(permissionVO);
            }

            if (permission.getLevelId() == 2) {
                MenuNodeVO permissionVO = new MenuNodeVO();
                BeanUtils.copyProperties(permission, permissionVO);
                // 判断是否已经创建了与pid对应的二级菜单集合或者三级按钮集合
                if (twoMap.get(permission.getPid()) == null) {
                    // 初始化二级菜单集合
                    List<MenuNodeVO> list = new ArrayList<>();
                    list.add(permissionVO);
                    twoMap.put(permission.getPid(), list);
                } else {
                    // 直接放到与pid对应的二级菜单集合中或者三级按钮集合中
                    twoMap.get(permission.getPid()).add(permissionVO);
                }
                permissionWarpper.getPaths().add(permission.getPath());
            }

            if (permission.getLevelId() == 3) {
                // 判断是否已经创建了与pid对应的二级菜单集合或者三级按钮集合
                if (threeMap.get(permission.getPid()) == null) {
                    // 初始化二级菜单集合
                    List<Permission> list = new ArrayList<>();
                    list.add(permission);
                    threeMap.put(permission.getPid(), list);
                } else {
                    // 直接放到与pid对应的二级菜单集合中或者三级按钮集合中
                    threeMap.get(permission.getPid()).add(permission);
                }
                permissionWarpper.getBtns().add(permission.getKey());
            }
        }
        // 完善一级菜单下的二级菜单，以及二级菜单下三级按钮
        for (MenuVO item : permissionVOList) {
            // 获取一级菜单的ID
            Long id = item.getId();
            // 通过一级菜单ID获取对应的二级菜单集合
            List<MenuNodeVO> twolist = twoMap.get(id);
            item.setChildren(twolist);
            // 通过二级菜单的ID获取对应三级按钮的集合
            if (twolist != null) {
                for (MenuNodeVO twoItem : twolist) {
                    // 获取二级菜单的ID
                    Long twoItemId = twoItem.getId();
                    // 通过一级菜单ID获取对应的二级菜单集合
                    List<Permission> threeList = threeMap.get(twoItemId);
                    twoItem.setChildren(threeList);
                }
            }
        }
        permissionWarpper.setItems(permissionVOList);
        return permissionWarpper;
    }
}
