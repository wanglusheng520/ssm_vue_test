package com.beneway.admin.service.sys_user;


import com.beneway.admin.entity.sys_user.LoginFo;
import com.beneway.common.common.result.Result;

public interface AdminSysUserService {

    /**
     * 登录
     * @param loginFo
     * @return
     */
    Result login(LoginFo loginFo);

}
