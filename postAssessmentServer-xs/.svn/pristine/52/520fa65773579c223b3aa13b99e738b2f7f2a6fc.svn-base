package com.beneway.common.service.satanswer;

import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.entity.answerrecord.AnswerRecord;
import com.beneway.common.entity.detailtarget.vo.DetailTargetVo;
import com.beneway.common.entity.prounitovertime.ProUnitOvertime;
import com.beneway.common.entity.satanswer.SatAnswer;
import com.beneway.common.entity.scheme.Scheme;
import com.beneway.common.entity.scheme.vo.SchemeVo;
import com.beneway.common.entity.schemeagency.SchemeAgency;

import java.util.List;

public interface SatAnswerService extends IService<SatAnswer> {

    /**
     * 填报时调用
     */
    void saveAnswer(List<DetailTargetVo> detailTargetVoList, AnswerRecord answerRecord, SchemeAgency schemeAgency, ProUnitOvertime proUnitOvertime);

    /**
     * 获取方案详情时调用
     */
    void getSatAnswer(DetailTargetVo detailTargetVo, SchemeVo schemeVo);
}
