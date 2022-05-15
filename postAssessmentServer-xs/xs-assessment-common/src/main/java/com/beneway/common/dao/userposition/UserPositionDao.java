package com.beneway.common.dao.userposition;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beneway.common.entity.userposition.UserPosition;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 
 *
 * @author zhy
 * @email 2434017367@qq.com
 * @date 2021-07-29 09:53:20
 */
@Mapper
@Repository
public interface UserPositionDao extends BaseMapper<UserPosition> {

}
