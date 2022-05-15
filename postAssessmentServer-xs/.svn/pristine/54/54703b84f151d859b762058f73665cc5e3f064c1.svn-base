package com.beneway.common.entity.startagency.vo;

import com.beneway.common.common.enums.AssessmentTypeEnums;
import com.beneway.common.entity.startagency.StartAgency;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class StartAgencyVo extends StartAgency {

    private String unitName;

    private String typeName;

    private String typeColor = "red";

    public StartAgency setType(String type){
        super.setType(type);
        if(StringUtils.isNotEmpty(type)){
            this.typeName = AssessmentTypeEnums.getByType(type).getDescribe();
            this.typeColor = AssessmentTypeEnums.getByType(type).getColor();
        }else{
            this.typeName = "未匹配到数据类型";
        }
        return null;
    }
}
