package com.beneway.common.service.system.role;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.common.utils.base.PageUtils;
import com.beneway.common.dao.system.role.RoleDao;
import com.beneway.common.entity.system.Result;
import com.beneway.common.entity.system.login.Loginuser;
import com.beneway.common.entity.system.menu.Menu;
import com.beneway.common.entity.system.role.Role;
import com.beneway.common.entity.system.rolemenu.RoleMenu;
import com.beneway.common.entity.system.userrole.UserRole;
import com.beneway.common.entity.userposition.UserPosition;
import com.beneway.common.service.system.login.LoginuserService;
import com.beneway.common.service.system.menu.MenuService;
import com.beneway.common.service.system.rolememu.RoleMenuService;
import com.beneway.common.service.system.userrole.UserRoleService;
import com.beneway.common.service.userposition.UserPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserPositionService userPositionService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private LoginuserService loginuserService;

    /**
     * 分页查询
     */
    @Override
    public IPage<Role> queryPage(Map<String, Object> params) {
        Page page = PageUtils.getPage(params);
        String keyword = (String) params.get("keyword");
        String type = (String) params.get("type");
        page = this.page(page, new LambdaQueryWrapper<Role>()
                .eq(StrUtil.isNotEmpty(type), Role::getType, type)
                .like(StrUtil.isNotEmpty(keyword), Role::getRoleName, keyword)
                .orderByAsc(Role::getId));

        List<Role> records = (List<Role>)page.getRecords();
        for (Role role : records) {
            if ("U".equals(role.getType())){
                List<UserRole> userRoleList = userRoleService.list(new LambdaQueryWrapper<UserRole>().eq(UserRole::getRoleId, role.getId()));
                List<String> userIdList = userRoleList.stream().map(UserRole::getUserId).collect(Collectors.toList());
                List<Loginuser> loginuserList = new ArrayList<>(userIdList.size());
                for (String userId : userIdList) {
                    Loginuser loginuser = loginuserService.getSafeById(userId);
                    loginuserList.add(loginuser);
                }
                role.setUserList(loginuserList);
            }
        }

        return page;
    }

    /**
     * 获取单个角色
     */
    @Override
    public Result find(Role role) {
        Integer id = role.getId();
        Role r = this.getById(id);
        // 获取菜单id
        List<RoleMenu> menuList = roleMenuService.list(new LambdaQueryWrapper<RoleMenu>()
                .select(RoleMenu::getMenuId)
                .eq(RoleMenu::getRoleId, id));
        if (CollUtil.isNotEmpty(menuList)){
            List<Integer> menuIdList = menuList.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
            r.setMenuIds(menuIdList);
        }
        return Result.success(r);
    }

    /**
     * 删除角色
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result delete(Role role) {
        int i = roleDao.deleteById(role);
        //删完角色删角色对应的菜单
        roleMenuService.delete(role.getId());
        return Result.check(i);
    }

    /**
     * 添加角色
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result insert(Role role) {
        int i = roleDao.insert(role);
        //设置角色对应菜单
        roleMenuService.insert(role.getMenuIds(),role.getId());
        return Result.check(i);
    }

    /**
     * 编辑角色
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result update(Role role) {
        int i = roleDao.updateById(role);
        //更新角色已有菜单
        if (null!=role.getMenuIds()){
            roleMenuService.update(role.getMenuIds(),role.getId());
        }
        return Result.check(i);
    }

    /**
     * 获取用户菜单列表
     * @param userId
     * @return
     */
    @Override
    public List<Menu> getUserMenuList(String userId){
        // 获取用户角色、单位类型
        List<UserPosition> userPositionList = userPositionService.list(new LambdaQueryWrapper<UserPosition>().eq(UserPosition::getUserId, userId));
        // 角色
        List<Integer> positionList = userPositionList.stream().map(UserPosition::getPosition).collect(Collectors.toList());
        // 单位类型
        Integer agencyPosition = userPositionList.get(0).getAgencyPosition();

        /*
            获取所有角色权限列表进行一一判断
         */
        List<Role> list = this.list(new LambdaQueryWrapper<Role>().eq(Role::getType, "P"));
        List<Integer> roleIdList = new LinkedList<>();
        for (Role role : list) {
            String agencyPositions = role.getAgencyPositions();
            boolean ab = false;
            if (StrUtil.isNotEmpty(agencyPositions)){
                ab = Arrays.asList(agencyPositions.split(",")).contains(String.valueOf(agencyPosition));
            }else{
                ab = true;
            }

            String userPositions = role.getUserPositions();
            boolean ub = false;
            if (StrUtil.isNotEmpty(userPositions)){
                List<String> asList = Arrays.asList(userPositions.split(","));
                for (Integer integer : positionList) {
                    if (asList.contains(String.valueOf(integer))){
                        ub = true;
                        break;
                    }
                }
            }else{
                ub = true;
            }

            if (ab && ub){
                roleIdList.add(role.getId());
            }
        }

        // 获取用户对应的role
        List<UserRole> userRoleList = userRoleService.list(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId));
        List<Integer> userRoleIdList = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(userRoleIdList)){
            roleIdList.addAll(userRoleIdList);
        }

        // 获取对应的菜单信息
        List<RoleMenu> roleMenuList = roleMenuService.list(new LambdaQueryWrapper<RoleMenu>()
                .select(RoleMenu::getMenuId)
                .in(RoleMenu::getRoleId, roleIdList));

        List<Integer> menuIdList = roleMenuList.stream().map(RoleMenu::getMenuId).distinct().collect(Collectors.toList());

        List<Menu> menuList = menuService.list(new QueryWrapper<Menu>().in("id", menuIdList).orderByAsc("sequence+0"));

        return menuList;

    }

}
