package com.beneway.common.entity.scheme.vo;

import com.alibaba.fastjson.JSON;
import com.beneway.common.entity.detailtarget.fo.DetailTargetFo;
import com.beneway.common.entity.prounitovertime.ProUnitOvertime;
import com.beneway.common.entity.scheme.Scheme;
import lombok.Data;

import java.util.List;

@Data
public class SchemeListFo extends Scheme {
    //填报期数
    private List<ProUnitOvertime> proUnitOvertimeList;
    //指标明细
    private List<DetailTargetFo> detailTargetFos;

    public static String setTarget(SchemeListFo schemeListFo){
        if(!schemeListFo.detailTargetFos.isEmpty()){
            return JSON.toJSONString(schemeListFo.detailTargetFos);
        }
        return "";
    }
}
