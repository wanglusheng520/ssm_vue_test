package com.beneway.common.dao.system.rolemenu;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beneway.common.entity.system.rolemenu.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RoleMenuDao extends BaseMapper<RoleMenu> {
}
