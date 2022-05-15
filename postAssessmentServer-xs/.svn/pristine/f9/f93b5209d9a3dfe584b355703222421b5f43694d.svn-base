package com.beneway.common.entity.majorindicators.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.beneway.common.common.enums.AssessmentTypeEnums;
import com.beneway.common.entity.detailtarget.DetailTarget;
import com.beneway.common.entity.detailtarget.vo.DetailTargetVo;
import com.beneway.common.entity.majorindicators.MajorIndicators;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
public class MajorIndicatorsVo extends MajorIndicators {

    private List<? extends  DetailTarget> detailTarget;

    private List<DetailTargetVo> detailTargetVoList;

    private String typeName;

    private String typeColor;

    public MajorIndicators setTypeFirst(String typeFirst){
        super.setTypeFirst(typeFirst);
        if(StringUtils.isNotEmpty(typeFirst)){
            this.typeName = AssessmentTypeEnums.getByType(typeFirst).getDescribe();
            this.typeColor = AssessmentTypeEnums.getByType(typeFirst).getColor();
        }else{
            this.typeName = "未匹配到数据类型";
        }
        return null;
    }


}
