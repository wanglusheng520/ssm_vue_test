package com.beneway.common.dao.majorindicators;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beneway.common.entity.majorindicators.MajorIndicators;
import com.beneway.common.entity.majorindicators.fo.MajorIndicatorsQueryFo;
import com.beneway.common.entity.majorindicators.vo.MajorIndicatorsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface MajorIndicatorsDao extends BaseMapper<MajorIndicators> {
    Page<MajorIndicatorsVo> queryPage(Page page,@Param("param") MajorIndicatorsQueryFo majorIndicatorsFo);

    Integer selectMar(@Param("param") Map<String, Object> param);

    Integer selectDetail(@Param("param") Map<String, Object> param);
}
