package com.beneway.admin.controller.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beneway.common.common.result.Result;
import com.beneway.common.system.entity.sys_role.SysRole;
import com.beneway.common.system.entity.sys_role.enums.SysRoleTypeEnum;
import com.beneway.common.system.entity.sys_role.fo.SysRoleFo;
import com.beneway.common.system.entity.sys_role.fo.SysRolePageQueryFo;
import com.beneway.common.system.entity.sys_role.vo.SysRoleVo;
import com.beneway.common.system.service.sys_role.SysRoleService;
import com.beneway.web.common.annotation.ReqApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Create by zhy on 2022/3/1 15:56
 */
@RestController
@RequestMapping("/admin_sys_role")
public class AdminSysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @ReqApi(value = "角色新增", permission = "sys:role:add")
    @PostMapping("/add")
    public Result add(@RequestBody SysRoleFo sysRoleFo){
        return sysRoleService.add(sysRoleFo);
    }

    @ReqApi(value = "角色编辑", permission = "sys:role:edit")
    @PutMapping("/edit")
    public Result edit(@RequestBody SysRoleFo sysRoleFo){
        return sysRoleService.edit(sysRoleFo);
    }

    @ReqApi(value = "角色删除", permission = "sys:role:del")
    @DeleteMapping("/del")
    public Result del(@RequestParam Integer id){
        return sysRoleService.del(id);
    }

    @ReqApi(value = "角色管理分页查询", permission = "sys:role:queryPage")
    @PostMapping("/queryPage")
    public Result queryPage(@RequestBody SysRolePageQueryFo sysRolePageQueryFo){
        Page<SysRoleVo> page = sysRoleService.queryPage(sysRolePageQueryFo);
        return Result.ok(page);
    }

    @ReqApi(value = "获取角色列表", permission = "sys:role:getList")
    @GetMapping("/getList")
    public Result getList(String type){
        SysRoleTypeEnum sysRoleTypeEnum = SysRoleTypeEnum.getByKey(type);
        List<SysRole> list = sysRoleService.getList(sysRoleTypeEnum);
        return Result.ok(list);
    }

}
