package com.beneway.common.dao.contractinfo;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beneway.common.entity.contractinfo.ContractInfo;
import com.beneway.common.entity.userlogin.UserloginLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface ContractInfoDao extends BaseMapper<ContractInfo> {

    IPage<ContractInfo> queryPage(Page page, @Param("param") Map<String, Object> params);

}
