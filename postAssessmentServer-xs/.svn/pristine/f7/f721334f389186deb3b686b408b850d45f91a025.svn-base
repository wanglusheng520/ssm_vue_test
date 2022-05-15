package com.beneway.common.service.prounitovertime;

import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.common.result.Result;
import com.beneway.common.entity.prounitovertime.ProUnitOvertime;
import com.beneway.common.entity.scheme.Scheme;

import java.util.List;

public interface ProUnitOvertimeService extends IService<ProUnitOvertime> {

    /**
     * 保存填报周期
     * @param proUnitOvertimeList
     * @param scheme
     */
    void saveList(List<ProUnitOvertime> proUnitOvertimeList, Scheme scheme);

    Result detailProUnitOverTime(String assId, String assType);
}
