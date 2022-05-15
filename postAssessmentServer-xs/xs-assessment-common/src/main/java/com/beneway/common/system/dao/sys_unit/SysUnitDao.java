package com.beneway.common.system.dao.sys_unit;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beneway.common.system.entity.sys_unit.SysUnit;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 *
 *
 * @author zhy
 * @email 2434017367@qq.com
 * @date 2022-02-23 19:13:49
 */
@Mapper
@Repository
public interface SysUnitDao extends BaseMapper<SysUnit> {

}
