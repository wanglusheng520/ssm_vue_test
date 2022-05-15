package com.beneway.common.service.targettemplate;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.common.result.Result;
import com.beneway.common.entity.targettemplate.TargetTemplate;
import com.beneway.common.entity.targettemplate.fo.TargetTemplateFo;
import com.beneway.common.entity.targettemplate.fo.TargetTemplateFoQuery;

public interface TargetTemplateService extends IService<TargetTemplate> {
    Result add(TargetTemplateFoQuery targetTemplate);

    Page<TargetTemplate> queryPage(TargetTemplateFoQuery targetTemplateFoQuery);

//
//    Result del(TargetTemplate targetTemplate);
//
    Result getOneDetail(TargetTemplate targetTemplate);

    Result edit(TargetTemplateFo targetTemplate);

    Result allTemplateByType(TargetTemplate targetTemplate);
//
//    Result detailTarget(TargetTemplate targetTemplate);
}
