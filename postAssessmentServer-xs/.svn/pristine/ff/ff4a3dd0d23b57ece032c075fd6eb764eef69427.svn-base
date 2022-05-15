package com.beneway.common.service.majorproject;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.common.result.Result;
import com.beneway.common.entity.majorproject.MajorProject;
import com.beneway.common.entity.majorproject.fo.MajorProjectQueryFo;
import com.beneway.common.entity.majorproject.vo.MajorProjectVo;

import java.util.Map;

public interface MajorProjectService extends IService<MajorProject> {

    Page<MajorProjectVo> queryPage(MajorProjectQueryFo majorProjectQueryFo);

    Result delete(MajorProject majorProject);

    MajorProjectVo getDetail(String id);

    Result ruku();

    Result zfandsh();
}
