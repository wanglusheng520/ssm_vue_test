package com.beneway.common.dao.normativedoc;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beneway.common.entity.majorproject.fo.MajorProjectQueryFo;
import com.beneway.common.entity.majorproject.vo.MajorProjectVo;
import com.beneway.common.entity.normativedoc.NormativeDoc;
import com.beneway.common.entity.normativedoc.fo.NormativeDocQueryFo;
import com.beneway.common.entity.normativedoc.vo.NormativeDocVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface NormativeDocDao extends BaseMapper<NormativeDoc> {

    Page<NormativeDocVo> queryPage(Page page, @Param("param") NormativeDocQueryFo normativeDocQueryFo);

    Integer amount();
}
