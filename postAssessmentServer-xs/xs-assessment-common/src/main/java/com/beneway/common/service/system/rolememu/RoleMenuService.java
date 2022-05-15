package com.beneway.common.service.system.rolememu;

import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.entity.system.menu.Menu;
import com.beneway.common.entity.system.rolemenu.RoleMenu;

import java.util.List;

public interface RoleMenuService extends IService<RoleMenu> {
    void insert(List<Integer> menuId, Integer roleId);

    void delete(Integer roleId);

    void update(List<Integer> menuId, Integer roleId);

}
