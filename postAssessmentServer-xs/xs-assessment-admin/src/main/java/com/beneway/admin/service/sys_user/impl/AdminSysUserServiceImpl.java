package com.beneway.admin.service.sys_user.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.beneway.admin.entity.sys_user.LoginFo;
import com.beneway.admin.entity.sys_user.LoginVo;
import com.beneway.admin.service.sys_user.AdminSysUserService;
import com.beneway.common.common.result.Result;
import com.beneway.common.system.entity.sys_user.SysUser;
import com.beneway.common.system.service.sys_user.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("adminSysUserService")
public class AdminSysUserServiceImpl implements AdminSysUserService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result login(LoginFo loginFo) {
        String account = loginFo.getAccount();
        String password = loginFo.getPassword();
        password = sysUserService.passEncr(password);
        SysUser sysUser = sysUserService.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getAccount, account)
                .eq(SysUser::getPassword, password));
        if (sysUser != null){
            // 封装登录成功后的用户信息
            StpUtil.login(sysUser.getId());
            LoginVo loginVo = new LoginVo(sysUser);
            loginVo.setToken(StpUtil.getTokenValue());
            return Result.ok(loginVo);
        }else{
            return Result.error("账号或密码错误");
        }

    }

}
