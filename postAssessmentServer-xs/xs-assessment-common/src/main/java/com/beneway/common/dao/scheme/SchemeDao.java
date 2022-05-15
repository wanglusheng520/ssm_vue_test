package com.beneway.common.dao.scheme;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beneway.common.entity.scheme.Scheme;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SchemeDao extends BaseMapper<Scheme> {
}
