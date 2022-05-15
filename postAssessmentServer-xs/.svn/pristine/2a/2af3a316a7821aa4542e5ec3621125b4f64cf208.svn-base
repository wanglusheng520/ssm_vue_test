package com.beneway.common.dao.system.agency;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beneway.common.entity.system.agency.Agency;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface AgencyDao extends BaseMapper<Agency> {
    List<Agency> find();
    IPage<Agency> queryPage(Page page, @Param("param") Map<String, Object> param);
    List<Agency> getUserAgencyTree(Agency agency);

    List<Agency> getAgencyList(Agency agency);
}
