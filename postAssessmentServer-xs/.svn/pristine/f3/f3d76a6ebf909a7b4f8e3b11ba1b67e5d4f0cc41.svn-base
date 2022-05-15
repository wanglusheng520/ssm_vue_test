package com.beneway.common.dao.system.login;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beneway.common.entity.system.login.Loginuser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface LoginuserDao extends BaseMapper<Loginuser> {

    Loginuser login(Loginuser o);

    IPage<Loginuser> queryPage(Page page, @Param("param") Map<String, Object> params);

    IPage<Loginuser> queryPages(Page page, @Param("param") Map<String, Object> params);
}
