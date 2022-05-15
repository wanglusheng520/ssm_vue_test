package com.beneway.common.earlywarn.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.common.result.Result;
import com.beneway.common.earlywarn.entity.EarlyWarn;

public interface EarlyWarnService extends IService<EarlyWarn> {
    Result earlyWarn();

    Result earlyWarnCount();

    String isHaveNoStandard(String assId , String assType);

    Result assEarly(EarlyWarn earlyWarn);
}
