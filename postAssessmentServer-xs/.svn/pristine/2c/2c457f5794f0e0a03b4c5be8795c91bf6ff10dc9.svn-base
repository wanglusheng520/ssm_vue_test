package com.beneway.web.controller.system.menu;

import com.beneway.common.entity.system.Result;
import com.beneway.common.entity.system.menu.Menu;
import com.beneway.common.service.system.menu.MenuService;
import com.beneway.common.service.system.rolememu.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    MenuService menuService;
    @Autowired
    RoleMenuService roleMenuService;

    /**
     * 获取用户系统菜单
     * @return
     */
    @GetMapping("/getUserSysMenu")
    public Result getUserSysMenu(){
        List<Menu> list = menuService.getUserSysMenu();
        return Result.success(list);
    }

    /**
     * 获取对应系统的菜单
     * @param id
     * @return
     */
    @GetMapping("/getSysMenus")
    public Result getSysMenus(@RequestParam("id") Integer id){
        List<Menu> menus = menuService.getSysMenus(id);
        return Result.success(menus);
    }

    @GetMapping("/getTreeMenus")
    public Result getTreeMenus(@RequestParam(value = "isFolder", defaultValue = "false") boolean isFolder){
        return Result.success(menuService.getTreeMenus(isFolder));
    }

    @GetMapping("/getNormal")
    public Result getNormal(){
        List<Menu> menuList = menuService.getNormal();
        return Result.success(menuList);
    }

    @GetMapping("/")
    public Result find(Menu menu){
        return menuService.find(menu);
    }

    @PostMapping("/")
    public Result insert(Menu menu){
        return menuService.insert(menu);
    }

    @PutMapping("/")
    public Result update(Menu menu){
        return menuService.update(menu);
    }

    @DeleteMapping("/")
    public Result delete(Menu menu){
        return menuService.delete(menu);
    }

}
