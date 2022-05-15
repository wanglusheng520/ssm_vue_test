package com.beneway.common.service.schemetargethistoryanswer;

import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.entity.schemetarget.vo.SchemeTargetVo;
import com.beneway.common.entity.schemetargethistoryanswer.SchemeTargetHistoryAnswer;

import java.util.List;


public interface SchemeTargetHistoryAnswerService extends IService<SchemeTargetHistoryAnswer> {
    /**
     * 方案详情调用
     */
    void getHistoryAnswerList(List<SchemeTargetVo> schemeTargetVoList);
}
