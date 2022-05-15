package com.beneway.common.system.service.sys_menu;

import com.beneway.common.common.mybatis.MyIService;
import com.beneway.common.system.entity.sys_menu.SysMenuPermission;

import java.util.List;

/**
 *
 *
 * @author zhy
 * @email 2434017367@qq.com
 * @date 2022-02-23 11:44:57
 */
public interface SysMenuPermissionService extends MyIService<SysMenuPermission> {

    /**
     * 添加权限
     * @param menuId
     * @param permissionList
     */
    void addPermission(Integer menuId, List<String> permissionList);

    /**
     * 移除权限
     * @param menuId
     */
    void removePermission(Integer menuId);

    /**
     * 根据菜单id列表获取权限列表
     * @param menuIdList
     * @return
     */
    List<String> getPermissionByMenuIdList(List<Integer> menuIdList);

}

