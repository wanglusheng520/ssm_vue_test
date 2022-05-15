package com.beneway.common.service.schemeagency;


import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.entity.detailtarget.fo.DetailTargetFo;
import com.beneway.common.entity.prounitovertime.ProUnitOvertime;
import com.beneway.common.entity.scheme.Scheme;
import com.beneway.common.entity.scheme.vo.SchemeVo;
import com.beneway.common.entity.schemeagency.SchemeAgency;

import java.util.List;

public interface SchemeAgencyService extends IService<SchemeAgency> {
    /**
     * 制定方案调用
     * @param detailTargetFoList
     * @param scheme
     */
    String saveList(List<DetailTargetFo> detailTargetFoList, Scheme scheme);

    /**
     * 填报时调用
     * 更新填报状态
     */
    void updateMark(SchemeVo schemeVo,SchemeAgency schemeAgency,List<ProUnitOvertime> proUnitOvertimeList);


}
