package com.beneway.common.service.zgfeedback;

import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.common.result.Result;
import com.beneway.common.entity.zgfeedback.ZgFeedback;


public interface ZgFeedbackService extends IService<ZgFeedback> {
    Result feedback(ZgFeedback zgFeedback);
}
