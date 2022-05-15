package com.beneway.common.entity.schemetarget.vo;

import com.beneway.common.common.enums.CompareEnum;
import com.beneway.common.entity.detailtarget.vo.DetailTargetVo;
import com.beneway.common.entity.schemetarget.SchemeTarget;
import com.beneway.common.entity.schemetargethistoryanswer.SchemeTargetHistoryAnswer;
import lombok.Data;

import java.util.List;

@Data
public class SchemeTargetVo extends SchemeTarget {

    private DetailTargetVo detailTargetVo;

    private CompareEnum compareEnum;

    private List<SchemeTargetHistoryAnswer> schemeTargetHistoryAnswerList;

}
