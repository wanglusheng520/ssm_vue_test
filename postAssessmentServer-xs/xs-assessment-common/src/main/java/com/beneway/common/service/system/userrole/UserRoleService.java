package com.beneway.common.service.system.userrole;

import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.entity.system.userrole.UserRole;

import java.util.List;

public interface UserRoleService extends IService<UserRole> {

    void delete(String userId);

    void insert(String userId, List<Integer> roleIdList);

}
