package com.beneway.web.controller.system.role;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.beneway.common.common.utils.base.PageUtils;
import com.beneway.common.entity.system.Result;
import com.beneway.common.entity.system.role.Role;
import com.beneway.common.service.system.role.RoleService;
import com.beneway.common.service.system.rolememu.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;
    @Autowired
    RoleMenuService roleMenuService;

    @GetMapping("/queryPage")
    public Result queryPage(@RequestParam Map<String, Object> params){
        IPage<Role> roleIPage = roleService.queryPage(params);
        return PageUtils.getPageResult(roleIPage);
    }

    @GetMapping("/getRoleList")
    public Result getRoleList(String type){
        List<Role> roleList = roleService.list(new LambdaQueryWrapper<Role>()
                .eq(Role::getType, type)
                .orderByAsc(Role::getId));
        return Result.success(roleList);
    }

    @GetMapping("/getRoleLists")
    public Result getRoleList(){
        List<Role> roleList = roleService.list(new LambdaQueryWrapper<Role>()
                        .eq(Role::getIsWork , 0)
                .orderByAsc(Role::getId));
        return Result.success(roleList);
    }

    @GetMapping("/")
    public Result find(Role role){
        return roleService.find(role);
    }

    @DeleteMapping("/")
    public Result delete(Role role){
        return roleService.delete(role);
    }

    @PostMapping("/")
    public Result insert(@RequestBody  Role role){
        return roleService.insert(role);
    }

    @PutMapping("/")
    public Result update(@RequestBody  Role role){
        return roleService.update(role);
    }
}
