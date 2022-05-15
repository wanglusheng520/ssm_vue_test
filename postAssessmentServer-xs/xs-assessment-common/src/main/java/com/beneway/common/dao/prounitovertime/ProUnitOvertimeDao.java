package com.beneway.common.dao.prounitovertime;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beneway.common.entity.prounitovertime.ProUnitOvertime;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProUnitOvertimeDao extends BaseMapper<ProUnitOvertime> {
    ProUnitOvertime getOne(String schemeId);
}
