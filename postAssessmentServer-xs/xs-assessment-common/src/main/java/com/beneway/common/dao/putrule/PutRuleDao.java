package com.beneway.common.dao.putrule;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beneway.common.entity.putrule.PutRule;
import com.beneway.common.entity.putrule.fo.PutRuleQueryFo;
import com.beneway.common.entity.putrule.vo.PutRuleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface PutRuleDao extends BaseMapper<PutRule> {
    Page<PutRuleVo> queryPage(Page page,@Param("param") PutRuleQueryFo putRuleQueryFo);
}
