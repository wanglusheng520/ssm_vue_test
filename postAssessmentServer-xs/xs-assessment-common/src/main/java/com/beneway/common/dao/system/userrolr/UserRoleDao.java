package com.beneway.common.dao.system.userrolr;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beneway.common.entity.system.userrole.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserRoleDao extends BaseMapper<UserRole> {
    List<Integer> getIds(String userId);
}
