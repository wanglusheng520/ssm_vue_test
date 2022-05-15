package com.beneway.common.service.system.menu;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.common.utils.base.Tools;
import com.beneway.common.common.utils.jwt.LoginUserUtils;
import com.beneway.common.dao.system.menu.MenuDao;
import com.beneway.common.entity.system.Result;
import com.beneway.common.entity.system.menu.Menu;
import com.beneway.common.entity.system.rolemenu.RoleMenu;
import com.beneway.common.service.system.role.RoleService;
import com.beneway.common.service.system.rolememu.RoleMenuService;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private RoleService roleService;

    /**
     * 获取用户系统菜单
     * @return
     */
    @Override
    public List<Menu> getUserSysMenu() {
        if (LoginUserUtils.isAdmin()){
            List<Menu> list = this.list(new LambdaQueryWrapper<Menu>().eq(Menu::getPid, 0));
            return list;
        }else{
            List<Menu> menuList = roleService.getUserMenuList(LoginUserUtils.getLoginUserId());
            menuList = menuList.stream().filter(m -> m.getPid().equals(0)).collect(Collectors.toList());
            return menuList;
        }
    }

    /**
     * 获取相应系统的菜单
     * @param menuId
     * @return
     */
    @Override
    public List<Menu> getSysMenus(Integer menuId) {
        if (LoginUserUtils.isAdmin()){
            List<Menu> treeMenus = getTreeMenus(false);
            for (Menu treeMenu : treeMenus) {
                if (menuId.equals(treeMenu.getId())){
                    List<Menu> list = treeMenu.getList();
                    paskMenuJump(list);
                    return list;
                }
            }
        }else{
            List<Menu> menuList = roleService.getUserMenuList(LoginUserUtils.getLoginUserId());
            Menu menu = menuList.stream().filter(m -> m.getId().equals(menuId)).collect(Collectors.toList()).get(0);
            comp(menuList, menu);
            List<Menu> list = menu.getList();
            paskMenuJump(list);
            return list;
        }

        return new ArrayList<>();
    }

    private void paskMenuJump(List<Menu> menuList){
        for (Menu menu : menuList) {
            String params = menu.getParams();
            if (StrUtil.isNotEmpty(params)){
                try {
                    StringBuffer sb = new StringBuffer(menu.getJump());
                    JsonObject jsonObject = new JsonParser().parse(params).getAsJsonObject();
                    Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();
                    Iterator<Map.Entry<String, JsonElement>> iterator = entries.iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, JsonElement> next = iterator.next();
                        String key = next.getKey();
                        String value = next.getValue().getAsString();
                        sb.append("/").append(key).append("=").append(value);
                    }
                    String url = sb.toString();
                    url = url.replaceAll("//", "/");
                    menu.setJump(url);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }
            List<Menu> list = menu.getList();
            if (CollUtil.isNotEmpty(list)){
                paskMenuJump(list);
            }
        }
    }

    /**
     * 显示 菜单树 专用接口，角色管理中的eleTree用
     */
    @Override
    public List<Menu> getTreeMenus(boolean isFolder) {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.and(isFolder, w -> w.isNull("jump").or().eq("jump", ""));
        wrapper.orderByAsc("sequence+0");
        List<Menu> menus = menuDao.selectList(wrapper);
        List<Menu> list = new ArrayList<>();
        for (Menu menu : menus) {
            if (menu.getPid()==0){
                list.add(menu);
                comp(menus,menu);
            }
        }
        return list;
    }

    /**
     * 获取所有菜单，treeTable需求数组格式
     */
    @Override
    public List<Menu> getNormal() {
        return menuDao.getAllMenus();
    }

    /**
     * 插入菜单
     */
    @Override
    public Result insert(Menu menu) {
        //插入前，设置排序号
        if (Tools.isEmpty(menu.getSequence())){
            menu.setSequence(menuDao.getMaxByPid(menu.getPid()));
        }
        int i = menuDao.insert(menu);
        return Result.check(i);
    }

    /**
     * 根据id查询菜单
     */
    @Override
    public Result find(Menu menu) {
        menu = menuDao.selectById(menu);
        return Result.check(menu);
    }

    /**
     * 根据id更新菜单
     */
    @Override
    public Result update(Menu menu) {
        int i = menuDao.updateById(menu);
        return Result.check(i);
    }

    /**
     * 根据id删除菜单
     * 菜单删除时，删掉其下所有子菜单，删掉他们对应的权限
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result delete(Menu menu) {
        int i = menuDao.deleteById(menu);
        del(menu);
        return Result.check(i);
    }

    /**
     * 递归整理菜单
     */
    private void comp(List<Menu> menus,Menu menu){
        List<Menu> list = new ArrayList<>();

        for (Menu m : menus) {
            //找到子菜单
            if (m.getPid().equals(menu.getId())){
                list.add(m);
                //递归获取子菜单
                comp(menus,m);
            }
        }
        menu.setList(list);
    }

    /**
     * 递归删除某一菜单其下所有子菜单
     * @param menu
     */
    private void del(Menu menu){
        //删除一批对应的权限
        QueryWrapper<RoleMenu> roleMenuWrapper = new QueryWrapper<>();
        roleMenuWrapper.eq("menu_id",menu.getId());
        roleMenuService.remove(roleMenuWrapper);

        //查询其下是否还有其他子菜单，如果还有则递归删除
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.eq("pid",menu.getId());
        List<Menu> menus = menuDao.selectList(wrapper);
        if (menus.size()>0){
            for (Menu m : menus) {
                del(m);
            }
        }

        //删除一层子菜单
        menuDao.delete(wrapper);
    }

}
