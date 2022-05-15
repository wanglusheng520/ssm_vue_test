package com.beneway.common.service.scheme;

import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.common.result.Result;
import com.beneway.common.entity.majorproject.vo.MajorProjectVo;
import com.beneway.common.entity.scheme.Scheme;
import com.beneway.common.entity.scheme.fo.SchemeFo;
import com.beneway.common.entity.scheme.vo.SchemeListFo;
import com.beneway.common.entity.scheme.vo.SchemeVo;
import com.beneway.common.entity.schemeagency.SchemeAgency;

import java.util.Date;

public interface SchemeService extends IService<Scheme> {

    Result addScheme(SchemeListFo schemeListFo);

    Result getTarget(Scheme scheme);

    Result zbtb(SchemeVo scheme);

    String getGradeOfPeriod(SchemeAgency sa, Date s, Date e);

    Result getSchemeTargetDetailNew(SchemeFo schemeFo);

    /**
     * 0未逾期 1 一个月以内 黄色 2 逾期 红色
     * @param assId
     * @return
     */
    String isOverdue(String assId);
}
