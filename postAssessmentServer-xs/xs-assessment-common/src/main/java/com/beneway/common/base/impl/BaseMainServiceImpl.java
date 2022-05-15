package com.beneway.common.base.impl;

import com.beneway.common.base.BaseMainService;
import com.beneway.common.base.BaseService;
import com.beneway.common.base.entity.BaseMain;
import com.beneway.common.common.enums.AssessmentTypeEnums;
import com.beneway.common.common.enums.StageEnum;
import com.beneway.common.common.utils.SpringContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BaseMainServiceImpl implements BaseMainService {


    private BaseService getMatterService(AssessmentTypeEnums assessmentTypeEnums){
        Class disposeClass = assessmentTypeEnums.getDisposeClass();
        BaseService baseService = (BaseService) SpringContextHolder.getBean(disposeClass);
        return baseService;
    }

    @Override
    public void updateMain(BaseMain baseMain, StageEnum stageEnum) {
        AssessmentTypeEnums assessmentTypeEnums = AssessmentTypeEnums.getByType(baseMain.getAssType());
        BaseService baseService = getMatterService(assessmentTypeEnums);
        baseService.updateStage(stageEnum,baseMain.getAssId());
    }
}
