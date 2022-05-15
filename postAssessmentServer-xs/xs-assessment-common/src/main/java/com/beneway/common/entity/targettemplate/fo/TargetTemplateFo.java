package com.beneway.common.entity.targettemplate.fo;

import com.beneway.common.entity.detailtarget.fo.DetailTargetFo;
import com.beneway.common.entity.targettemplate.TargetTemplate;
import lombok.Data;

import java.util.List;

@Data
public class TargetTemplateFo extends TargetTemplate {


    private List<DetailTargetFo> detailTargetFos;

}
