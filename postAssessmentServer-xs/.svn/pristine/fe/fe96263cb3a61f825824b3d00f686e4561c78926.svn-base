package com.beneway.common.system.service.sys_user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beneway.common.common.mybatis.MyIService;
import com.beneway.common.common.result.Result;
import com.beneway.common.system.entity.sys_user.SysUser;
import com.beneway.common.system.entity.sys_user.fo.SysUserFo;
import com.beneway.common.system.entity.sys_user.fo.SysUserQueryFo;
import com.beneway.common.system.entity.sys_user.vo.LoginUserInfo;
import com.beneway.common.system.entity.sys_user.vo.SysUserComVo;
import com.beneway.common.system.entity.sys_user.vo.SysUserVo;

public interface SysUserService extends MyIService<SysUser> {

    /**
     * 密码加密
     * @param password
     * @return
     */
    String passEncr(String password);

    /**
     * 用户新增
     * @param sysUserFo
     * @return
     */
    Result add(SysUserFo sysUserFo);

    /**
     * 用户编辑
     * @param sysUserFo
     * @return
     */
    Result edit(SysUserFo sysUserFo);

    /**
     * 用户删除
     * @param userId
     * @return
     */
    Result del(String userId);

    /**
     * 通过id获取登录时需要保存到request的用户信息类
     * @param userId
     * @return
     */
    LoginUserInfo getLoginUserInfo(String userId);

    /**
     * 判断用户是否有权限
     * @param userId
     * @param permission
     * @return
     */
    boolean isPermission(String userId, String permission);

    /**
     * 用户管理分页查询
     * @param sysUserPageQueryFo
     * @return
     */
    Page<SysUserVo> queryPage(SysUserQueryFo sysUserPageQueryFo);

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    SysUserVo getUserInfo(String userId);

    /**
     * 获取前端统一组件的用户信息
     * @return
     */
    SysUserComVo getComUserInfo(String userId);

}
