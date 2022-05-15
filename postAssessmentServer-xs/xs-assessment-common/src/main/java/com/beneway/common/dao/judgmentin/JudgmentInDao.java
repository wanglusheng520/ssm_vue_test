package com.beneway.common.dao.judgmentin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beneway.common.entity.judgmentin.JudgmentIn;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Description:
 *
 * @author zxc
 * @date 2022/2/22 14:50
 */
@Mapper
@Repository
public interface JudgmentInDao extends BaseMapper<JudgmentIn> {

}
