package com.beneway.common.system.dao.sys_menu;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beneway.common.system.entity.sys_menu.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 *
 *
 * @author zhy
 * @email 2434017367@qq.com
 * @date 2022-02-23 11:44:57
 */
@Mapper
@Repository
public interface SysMenuDao extends BaseMapper<SysMenu> {

}
