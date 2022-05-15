package com.beneway.common.entity.majorindicators.fo;

import com.beneway.common.entity.detailtarget.fo.DetailTargetFo;
import com.beneway.common.entity.majorindicators.MajorIndicators;
import lombok.Data;

@Data
public class MajorIndicatorsFo extends MajorIndicators {

    private DetailTargetFo detailTarget;

}
