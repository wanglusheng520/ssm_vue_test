package com.beneway.common.service.judgmentin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.common.result.Result;
import com.beneway.common.entity.judgmentin.JudgmentIn;
import com.beneway.common.entity.judgmentin.fo.JudgmentInFo;

/**
 * Description:
 *
 * @author zxc
 * @date 2022/2/22 14:51
 */

public interface JudgmentInService extends IService<JudgmentIn> {
    Result add(JudgmentInFo judgmentInFo);

    Result auto(JudgmentInFo judgmentInFo);
}
