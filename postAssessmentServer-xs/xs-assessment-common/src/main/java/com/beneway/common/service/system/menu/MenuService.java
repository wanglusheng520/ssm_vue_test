package com.beneway.common.service.system.menu;

import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.entity.system.Result;
import com.beneway.common.entity.system.login.Loginuser;
import com.beneway.common.entity.system.menu.Menu;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MenuService extends IService<Menu> {

    List<Menu> getUserSysMenu();

    List<Menu> getSysMenus(Integer type);

    List<Menu> getTreeMenus(boolean isFolder);

    List<Menu> getNormal();

    Result insert(Menu menu);
    Result find(Menu menu);
    Result update(Menu menu);
    Result delete(Menu menu);
}
