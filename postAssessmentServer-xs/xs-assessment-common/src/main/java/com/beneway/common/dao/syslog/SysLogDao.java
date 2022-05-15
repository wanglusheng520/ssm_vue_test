package com.beneway.common.dao.syslog;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beneway.common.entity.syslog.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author zxc
 * Date on 2020/3/5  13:57
 */
@Repository
@Mapper
public interface SysLogDao extends BaseMapper<SysLog> {

}
