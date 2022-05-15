package com.beneway.common.dao.startagency;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beneway.common.entity.putrule.fo.PutRuleQueryFo;
import com.beneway.common.entity.putrule.vo.PutRuleVo;
import com.beneway.common.entity.startagency.StartAgency;
import com.beneway.common.entity.startagency.fo.StartAgencyQueryFo;
import com.beneway.common.entity.startagency.vo.StartAgencyVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface StartAgencyDao extends BaseMapper<StartAgency> {
    Page<StartAgencyVo> queryPage(Page page, @Param("param") StartAgencyQueryFo startAgencyQueryFo);
}
