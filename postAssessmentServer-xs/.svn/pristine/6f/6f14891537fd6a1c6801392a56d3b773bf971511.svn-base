package com.beneway.common.service.system.rolememu;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.dao.system.rolemenu.RoleMenuDao;
import com.beneway.common.entity.system.menu.Menu;
import com.beneway.common.entity.system.rolemenu.RoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuDao, RoleMenu> implements RoleMenuService {
    @Autowired
    RoleMenuDao roleMenuDao;

    /**
     * 批量插入菜单
     */
    @Override
    public void insert(List<Integer> menuIds,Integer roleId) {
        RoleMenu roleMenu = RoleMenu.builder()
                .roleId(roleId)
                .build();
        for (Integer menuId : menuIds) {
            roleMenu.setMenuId(menuId);
            roleMenuDao.insert(roleMenu);
        }
    }

    /**
     * 批量删除菜单
     */
    @Override
    public void delete(Integer roleId) {
        QueryWrapper<RoleMenu> wrapper =
                new QueryWrapper<RoleMenu>().eq("role_id", roleId);
        roleMenuDao.delete(wrapper);
    }

    /**
     * 更新一个角色的权限：删除再添加
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<Integer> menuId, Integer roleId) {
        delete(roleId);
        insert(menuId,roleId);
    }

}
