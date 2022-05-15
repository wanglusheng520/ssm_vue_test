package com.beneway.common.system.service.sys_user;

import com.beneway.common.common.mybatis.MyIService;
import com.beneway.common.system.entity.sys_user.SysUserRole;

import java.util.List;

/**
 * Create by zhy on 2022/3/2 10:01
 */
public interface SysUserRoleService extends MyIService<SysUserRole> {

    /**
     * 添加用户角色
     * @param userId
     * @param roleIdList
     */
    void addUserRole(String userId, List<Integer> roleIdList);

    /**
     * 更新用户角色
     * @param userId
     * @param roleIdList
     */
    void updateUserRole(String userId, List<Integer> roleIdList);

    /**
     * 移除用户角色
     * @param userId
     */
    void removeUserRole(String userId);

}
