package com.beneway.admin.entity.sys_user;

import com.beneway.common.system.entity.sys_user.SysUser;
import lombok.Data;

import java.io.Serializable;

@Data
public class LoginVo implements Serializable {

    public LoginVo(SysUser sysUser){
        this.setAccount(sysUser.getAccount());
        this.setUsername(sysUser.getUsername());
    }

    private String username;

    private String account;

    private String token;


}
