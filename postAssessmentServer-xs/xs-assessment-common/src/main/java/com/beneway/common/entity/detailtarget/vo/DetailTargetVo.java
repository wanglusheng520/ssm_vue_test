package com.beneway.common.entity.detailtarget.vo;

import com.beneway.common.entity.choiceindicator.ChoiceIndicator;
import com.beneway.common.entity.detailtarget.DetailTarget;
import com.beneway.common.entity.detailtargetitem.DetailTargetItem;
import com.beneway.common.entity.detailtargetitem.vo.DetailTargetItemVo;
import com.beneway.common.entity.majorindicators.vo.MajorIndicatorsVo;
import com.beneway.common.entity.prounitovertime.ProUnitOvertime;
import com.beneway.common.entity.satanswer.vo.SatAnswerVo;
import com.beneway.common.entity.schemeagencytarget.vo.SchemeAgencyTargetVo;
import com.beneway.common.system.entity.sys_unit.vo.SysUnitComVo;
import lombok.Data;

import java.util.List;

@Data
public class DetailTargetVo extends DetailTarget {

    private List<ChoiceIndicator> choiceIndicator;

    private List<DetailTargetItem> detailTargetItem;

    private List<DetailTargetItemVo> detailTargetItemVoList;

    private SysUnitComVo sysUnitComVo;

    private String content;

    private String supportingMaterials;

    private SchemeAgencyTargetVo schemeAgencyTargetVo;

    private List<SatAnswerVo> satAnswerVoList;
}
