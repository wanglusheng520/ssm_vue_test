package com.beneway.common.entity.targettemplate.fo;

import com.beneway.common.common.utils.page.PageQuery;
import com.beneway.common.entity.detailtarget.fo.DetailTargetFo;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Builder
public class TargetTemplateFoQuery extends PageQuery {

    private String id;

    private String templateName;

    private String templateRemark;

    private List<DetailTargetFo> detailTargetFos;

    //查询使用
    private String search;

    private String type;

}
