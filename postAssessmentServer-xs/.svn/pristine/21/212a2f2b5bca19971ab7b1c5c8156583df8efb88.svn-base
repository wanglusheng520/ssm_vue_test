package com.beneway.admin.controller.system;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beneway.admin.entity.sys_user.LoginFo;
import com.beneway.admin.service.sys_user.AdminSysUserService;
import com.beneway.common.common.result.Result;
import com.beneway.common.common.utils.jwt.LoginUserUtils;
import com.beneway.common.system.entity.sys_user.fo.SysUserFo;
import com.beneway.common.system.entity.sys_user.fo.SysUserQueryFo;
import com.beneway.common.system.entity.sys_user.vo.SysUserVo;
import com.beneway.common.system.service.sys_user.SysUserService;
import com.beneway.web.common.annotation.ReqApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin_sys_user")
public class AdminSysUserController {

    @Autowired
    private AdminSysUserService adminSysUserService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录
     * @param loginFo
     * @return
     */
    @PostMapping("/login")
    public Result login(LoginFo loginFo){
        Result login = adminSysUserService.login(loginFo);
        return login;
    }

    /**
     * 退出登录
     * @return
     */
    @GetMapping("/logout")
    public Result logout(){
        StpUtil.logout();
        return Result.ok();
    }

    /**
     * 获取登录用户信息
     * @return
     */
    @GetMapping("/getUserInfo")
    public Result getUserInfo(){
        String loginUserId = LoginUserUtils.getLoginUserId();
        SysUserVo userInfo = sysUserService.getUserInfo(loginUserId);
        return Result.ok(userInfo);
    }

    /**
     * 用户新增
     * @param sysUserFo
     * @return
     */
    @ReqApi(value = "用户新增", permission = "sys:user:add")
    @PostMapping("/add")
    public Result add(@RequestBody SysUserFo sysUserFo){
        return sysUserService.add(sysUserFo);
    }

    /**
     * 用户编辑
     * @param sysUserFo
     * @return
     */
    @ReqApi(value = "用户编辑", permission = "sys:user:edit")
    @PutMapping ("/edit")
    public Result edit(@RequestBody SysUserFo sysUserFo){
        return sysUserService.edit(sysUserFo);
    }

    /**
     * 用户删除
     * @param userId
     * @return
     */
    @ReqApi(value = "用户删除", permission = "sys:user:del")
    @DeleteMapping ("/del")
    public Result del(@RequestParam String userId){
        return sysUserService.del(userId);
    }

    /**
     * 用户管理分页查询
     * @param sysUserPageQueryFo
     * @return
     */
    @ReqApi(value = "用户管理分页查询", permission = "sys:user:queryPage")
    @PostMapping("/queryPage")
    public Result queryPage(@RequestBody SysUserQueryFo sysUserPageQueryFo){
        Page<SysUserVo> page = sysUserService.queryPage(sysUserPageQueryFo);
        return Result.ok(page);
    }

}
