package com.beneway.common.service.normativedoc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.entity.normativedoc.NormativeDoc;
import com.beneway.common.entity.normativedoc.fo.NormativeDocQueryFo;
import com.beneway.common.entity.normativedoc.vo.NormativeDocVo;
import com.beneway.common.entity.system.Result;


public interface NormativeDocService extends IService<NormativeDoc> {
    Page<NormativeDocVo> queryPage(NormativeDocQueryFo normativeDocQueryFo);

    Result delete(NormativeDoc normativeDoc);

    Result ruku();

    NormativeDocVo getDetail(String id);

    com.beneway.common.common.result.Result amount();
}
