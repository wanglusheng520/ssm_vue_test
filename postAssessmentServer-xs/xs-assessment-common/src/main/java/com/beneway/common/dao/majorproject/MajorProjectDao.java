package com.beneway.common.dao.majorproject;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beneway.common.entity.majorproject.MajorProject;
import com.beneway.common.entity.majorproject.fo.MajorProjectQueryFo;
import com.beneway.common.entity.majorproject.vo.MajorProjectVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface MajorProjectDao extends BaseMapper<MajorProject> {

    Page<MajorProjectVo> queryPage(Page page, @Param("param")MajorProjectQueryFo majorProjectQueryFo);

    Integer zfsize();

    Integer shsize();
}
