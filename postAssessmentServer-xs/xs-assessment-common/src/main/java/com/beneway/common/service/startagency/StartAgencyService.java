package com.beneway.common.service.startagency;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.common.result.Result;
import com.beneway.common.entity.majorproject.fo.MajorProjectQueryFo;
import com.beneway.common.entity.startagency.StartAgency;
import com.beneway.common.entity.startagency.fo.StartAgencyFo;
import com.beneway.common.entity.startagency.fo.StartAgencyQueryFo;
import com.beneway.common.entity.startagency.vo.StartAgencyVo;


public interface StartAgencyService extends IService<StartAgency> {
    Page<StartAgencyVo> queryPage(StartAgencyQueryFo startAgencyQueryFo);

    Result del(String id);

    Result addOrUpdate(StartAgencyFo startAgencyFo);

    Result getById(String id);

    void setProjectQuery(MajorProjectQueryFo majorProjectQueryFo);



}
