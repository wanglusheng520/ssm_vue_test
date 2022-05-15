package com.beneway.common.dao.system.router;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beneway.common.entity.system.router.Router;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author wangJun
 * @createTime 2021-09-06
 */
@Repository
@Mapper
public interface RouterMapper extends BaseMapper<Router> {
}
