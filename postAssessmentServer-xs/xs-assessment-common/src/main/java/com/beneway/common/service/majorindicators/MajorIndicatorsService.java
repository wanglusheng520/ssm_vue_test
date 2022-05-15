package com.beneway.common.service.majorindicators;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.common.result.Result;
import com.beneway.common.entity.majorindicators.MajorIndicators;
import com.beneway.common.entity.majorindicators.fo.MajorIndicatorsFo;
import com.beneway.common.entity.majorindicators.fo.MajorIndicatorsQueryFo;
import com.beneway.common.entity.majorindicators.vo.MajorIndicatorsVo;

public interface MajorIndicatorsService extends IService<MajorIndicators> {

    Page<MajorIndicatorsVo> queryPage(MajorIndicatorsQueryFo majorIndicatorsFo);

    Result add(MajorIndicatorsFo majorIndicatorsFo);

    Result addTarget(MajorIndicatorsFo majorIndicatorsFo);

    Result del(MajorIndicators majorIndicators);

    Result getOneDetail(MajorIndicators majorIndicators);

    Result edit(MajorIndicators majorIndicators);

}
