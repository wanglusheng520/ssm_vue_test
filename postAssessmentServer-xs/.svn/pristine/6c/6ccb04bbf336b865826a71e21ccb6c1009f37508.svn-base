package com.beneway.common.service.system.role;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.entity.system.Result;
import com.beneway.common.entity.system.menu.Menu;
import com.beneway.common.entity.system.role.Role;

import java.util.List;
import java.util.Map;

public interface RoleService extends IService<Role> {

    IPage<Role> queryPage(Map<String, Object> params);

    Result find(Role role);
    Result delete(Role role);
    Result insert(Role role);
    Result update(Role role);

    /**
     * 获取用户菜单列表
     * @param userId
     * @return
     */
    List<Menu> getUserMenuList(String userId);

}
