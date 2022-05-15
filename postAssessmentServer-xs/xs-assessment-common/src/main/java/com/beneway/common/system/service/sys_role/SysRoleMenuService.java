package com.beneway.common.system.service.sys_role;

import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.system.entity.sys_role.SysRoleMenu;

import java.util.List;

/**
 *
 *
 * @author zhy
 * @email 2434017367@qq.com
 * @date 2022-03-01 14:59:39
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    List<Integer> getMenuIdList(Integer sysRoleId);

    void addRoleMenu(Integer sysRoleId, List<Integer> sysMenuIdList);

    void updateRoleMenu(Integer sysRoleId, List<Integer> sysMenuIdList);

    void removeRoleMenu(Integer sysRoleId);

}

