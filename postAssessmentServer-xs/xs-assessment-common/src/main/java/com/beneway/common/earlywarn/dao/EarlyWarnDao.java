package com.beneway.common.earlywarn.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beneway.common.earlywarn.entity.EarlyWarn;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface EarlyWarnDao extends BaseMapper<EarlyWarn> {

    List<EarlyWarn> indexEarlyWarn(Integer supUnitId);

    Integer earlyWarnCount();
}
