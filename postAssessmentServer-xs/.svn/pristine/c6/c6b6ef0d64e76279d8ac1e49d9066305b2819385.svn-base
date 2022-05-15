package com.beneway.admin.controller.system;

import com.beneway.common.common.result.Result;
import com.beneway.common.system.entity.sys_menu.fo.SysMenuFo;
import com.beneway.common.system.entity.sys_menu.vo.SysMenuVo;
import com.beneway.common.system.service.sys_menu.SysMenuService;
import com.beneway.web.common.annotation.ReqApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin_sys_menu")
public class AdminSysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 新增菜单
     * @param sysMenuFo
     * @return
     */
    @ReqApi(value = "菜单新增", permission = "sys:menu:add")
    @PostMapping("/add")
    public Result add(@RequestBody SysMenuFo sysMenuFo){
        return sysMenuService.add(sysMenuFo);
    }

    /**
     * 编辑菜单
     * @param sysMenuFo
     * @return
     */
    @ReqApi(value = "菜单编辑", permission = "sys:menu:edit")
    @PutMapping("/edit")
    public Result edit(@RequestBody SysMenuFo sysMenuFo){
        return sysMenuService.edit(sysMenuFo);
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @ReqApi(value = "菜单删除", permission = "sys:menu:del")
    @DeleteMapping("/del")
    public Result del(@RequestParam Integer id) {
        return sysMenuService.del(id);
    }

    /**
     * 获取树列表
     * @return
     */
    @ReqApi(value = "获取菜单树列表", permission = "sys:menu:getTreeList")
    @GetMapping("/getTreeList")
    public Result getTreeList(){
        List<SysMenuVo> treeList = sysMenuService.getTreeList();
        return Result.ok(treeList);
    }

    /**
     * 获取登录用户菜单
     * @return
     */
    @GetMapping("/getUserMenuList")
    public Result getUserMenuList(){
        List<SysMenuVo> userMenuList = sysMenuService.getUserMenuList();
        return Result.ok(userMenuList);
    }

}
