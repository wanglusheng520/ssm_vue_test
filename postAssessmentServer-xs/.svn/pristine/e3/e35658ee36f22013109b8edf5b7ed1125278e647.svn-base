package com.beneway.common.dao.detailtarget;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beneway.common.entity.detailtarget.DetailTarget;
import com.beneway.common.entity.detailtarget.fo.DetailTargetQueryFo;
import com.beneway.common.entity.detailtarget.vo.DetailTargetVo;
import com.beneway.common.entity.majorindicators.fo.MajorIndicatorsQueryFo;
import com.beneway.common.entity.majorindicators.vo.MajorIndicatorsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface DetailTargetDao extends BaseMapper<DetailTarget> {

    List<DetailTarget> yqzb(@Param("ids") List<String> ids);

    Page<DetailTargetVo> queryPage(Page page, @Param("param") DetailTargetQueryFo detailTargetQueryFo);

}
