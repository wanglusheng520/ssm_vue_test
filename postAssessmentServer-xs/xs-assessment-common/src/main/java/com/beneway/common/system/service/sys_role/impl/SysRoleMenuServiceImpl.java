package com.beneway.common.system.service.sys_role.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.system.dao.sys_role.SysRoleMenuDao;
import com.beneway.common.system.entity.sys_role.SysRoleMenu;
import com.beneway.common.system.service.sys_role.SysRoleMenuService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 *
 * @author zhy
 * @email 2434017367@qq.com
 * @date 2022-03-01 14:59:39
 */
@EnableAspectJAutoProxy( proxyTargetClass = true , exposeProxy = true )
@CacheConfig(cacheNames = "sysRoleMenu")
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenu> implements SysRoleMenuService {

    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;

    private SysRoleMenuService getCurrThis(){
        SysRoleMenuService currentProxy = (SysRoleMenuService) AopContext.currentProxy();
        return currentProxy;
    }

    @Cacheable(key = "'list[' + #sysRoleId + ']'")
    @Override
    public List<Integer> getMenuIdList(Integer sysRoleId) {
        List<SysRoleMenu> list = this.list(new LambdaQueryWrapper<SysRoleMenu>()
                .select(SysRoleMenu::getSysMenuId)
                .eq(SysRoleMenu::getSysRoleId, sysRoleId));
        List<Integer> sysMenuIdList = list.stream().map(SysRoleMenu::getSysMenuId).collect(Collectors.toList());
        return sysMenuIdList;
    }

    @CacheEvict(key = "'list[' + #sysRoleId + ']'")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addRoleMenu(Integer sysRoleId, List<Integer> sysMenuIdList) {
        List<SysRoleMenu> list = new LinkedList<>();
        for (Integer sysMenuId : sysMenuIdList) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setSysRoleId(sysRoleId);
            sysRoleMenu.setSysMenuId(sysMenuId);
            list.add(sysRoleMenu);
        }
        this.saveBatch(list);
    }

    @CacheEvict(key = "'list[' + #sysRoleId + ']'")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRoleMenu(Integer sysRoleId, List<Integer> sysMenuIdList) {
        removeRoleMenu(sysRoleId);
        addRoleMenu(sysRoleId, sysMenuIdList);
    }

    @CacheEvict(key = "'list[' + #sysRoleId + ']'")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeRoleMenu(Integer sysRoleId) {
        this.remove(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getSysRoleId, sysRoleId));
    }
}
