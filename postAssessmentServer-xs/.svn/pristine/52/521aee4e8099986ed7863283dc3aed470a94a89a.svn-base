package com.beneway.common.dao.targettemplate;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beneway.common.entity.targettemplate.TargetTemplate;
import com.beneway.common.entity.targettemplate.fo.TargetTemplateFoQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TargetTemplateDao extends BaseMapper<TargetTemplate> {
    Page<TargetTemplate> queryPage(Page page, @Param("param") TargetTemplateFoQuery targetTemplateFoQuery);
}
