package com.beneway.common.dao.message;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beneway.common.entity.message.Message;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageDao extends BaseMapper<Message> {
}
