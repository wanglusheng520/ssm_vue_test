package com.beneway.common.service.detailtarget;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.common.result.Result;
import com.beneway.common.entity.detailtarget.DetailTarget;
import com.beneway.common.entity.detailtarget.fo.DetailTargetFo;
import com.beneway.common.entity.detailtarget.fo.DetailTargetQueryFo;
import com.beneway.common.entity.detailtarget.vo.DetailTargetVo;
import com.beneway.common.entity.majorindicators.fo.MajorIndicatorsQueryFo;
import com.beneway.common.entity.majorindicators.vo.MajorIndicatorsVo;
import com.beneway.common.entity.schemeagencytarget.vo.SchemeAgencyTargetVo;

import java.util.List;

public interface DetailTargetService extends IService<DetailTarget> {
    Result del(DetailTarget detailTarget);

    Result add(DetailTargetFo detailTargetFo);

    Result addDetail(DetailTargetFo detailTargetFo);

    Result selectDetailTargetForIds(DetailTargetFo detailTargetFo);

    Result allTarget();


    Page<DetailTargetVo> queryPage(DetailTargetQueryFo detailTargetQueryFo);

    /**
     * 获取填报指标
     */
    List<DetailTargetVo> getTargetTB(List<SchemeAgencyTargetVo> schemeAgencyTargetVoList);

    void getDetailTargetAss(DetailTargetVo detailTargetVo);

}
