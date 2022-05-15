package com.beneway.common.dao.operationlog;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beneway.common.entity.operationlog.OperationLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OperationLogDao extends BaseMapper<OperationLog> {
    List<OperationLog> newAssesEvent(Integer supUnitId);

    Integer assessAmount();

    Integer yiAssess(String stage, String stage1, String stage2);

    Integer yizg(String stage);
}
