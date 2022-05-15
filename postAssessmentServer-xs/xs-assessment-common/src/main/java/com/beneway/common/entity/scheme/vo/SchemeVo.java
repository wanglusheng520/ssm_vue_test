package com.beneway.common.entity.scheme.vo;

import com.beneway.common.entity.detailtarget.vo.DetailTargetVo;
import com.beneway.common.entity.majorindicators.vo.MajorIndicatorsVo;
import com.beneway.common.entity.prounitovertime.ProUnitOvertime;
import com.beneway.common.entity.scheme.Scheme;
import com.beneway.common.entity.schemeagency.vo.SchemeAgencyVo;
import com.beneway.common.entity.schemetarget.vo.SchemeTargetVo;
import lombok.Data;

import java.util.List;

@Data
public class SchemeVo extends Scheme {
    List<SchemeAgencyVo> schemeAgencyVoList;
    List<SchemeTargetVo> schemeTargetVoList;
    List<MajorIndicatorsVo> majorIndicatorsVoList;
    List<DetailTargetVo> detailTargets;
    private ProUnitOvertime proUnitOvertime;
}
