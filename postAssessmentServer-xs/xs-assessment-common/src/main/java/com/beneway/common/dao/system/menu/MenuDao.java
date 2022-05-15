package com.beneway.common.dao.system.menu;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beneway.common.entity.system.login.Loginuser;
import com.beneway.common.entity.system.menu.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Mapper
public interface MenuDao extends BaseMapper<Menu> {

    ArrayList<Menu> getAllMenus();

    String getMaxByPid(Integer pid);
}
