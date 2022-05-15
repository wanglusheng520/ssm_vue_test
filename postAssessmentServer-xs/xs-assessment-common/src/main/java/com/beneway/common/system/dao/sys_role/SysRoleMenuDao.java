package com.beneway.common.system.dao.sys_role;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beneway.common.system.entity.sys_role.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 *
 *
 * @author zhy
 * @email 2434017367@qq.com
 * @date 2022-03-01 14:59:39
 */
@Mapper
@Repository
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenu> {

}
