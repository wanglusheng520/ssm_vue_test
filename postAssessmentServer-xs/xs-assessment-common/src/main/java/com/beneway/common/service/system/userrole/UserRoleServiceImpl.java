package com.beneway.common.service.system.userrole;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.dao.system.userrolr.UserRoleDao;
import com.beneway.common.entity.system.userrole.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRole> implements UserRoleService {

    @Autowired
    private UserRoleDao userRoleDao;

    /**
     * 删除一个用户的所有角色
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String userId) {
        if (StrUtil.isNotEmpty(userId)){
            this.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insert(String userId, List<Integer> roleIdList){
        if (StrUtil.isNotEmpty(userId) && CollUtil.isNotEmpty(roleIdList)){
            List<UserRole> userRoleList = new ArrayList<>(roleIdList.size());
            for (Integer roleId : roleIdList) {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                userRoleList.add(userRole);
            }
            this.saveBatch(userRoleList);
        }
    }

}
