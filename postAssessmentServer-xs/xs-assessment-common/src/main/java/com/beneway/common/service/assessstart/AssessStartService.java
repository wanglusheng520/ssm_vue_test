package com.beneway.common.service.assessstart;

import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.common.result.Result;
import com.beneway.common.entity.assessstart.AssessStart;
import com.beneway.common.entity.majorproject.MajorProject;
import com.beneway.common.entity.scheme.Scheme;

public interface AssessStartService extends IService<AssessStart> {

    Result getUnit(AssessStart AssessStart);

    /**
     * 制定方案时调用
     */
    void saveMakePlan(Scheme scheme,String xtUnit);

    Result myParticipation();

    Result getDetail(AssessStart assessStart);
}
