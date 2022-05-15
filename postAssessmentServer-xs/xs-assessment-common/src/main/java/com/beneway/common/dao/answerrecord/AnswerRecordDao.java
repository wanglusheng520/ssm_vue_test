package com.beneway.common.dao.answerrecord;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beneway.common.entity.answerrecord.AnswerRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AnswerRecordDao extends BaseMapper<AnswerRecord> {
    AnswerRecord newest(@Param("id") String id);
}
