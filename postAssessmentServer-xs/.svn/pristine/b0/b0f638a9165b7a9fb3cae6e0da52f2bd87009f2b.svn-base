package com.beneway.common.entity.putrule.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.beneway.common.common.enums.AssessmentTypeEnums;
import com.beneway.common.entity.putrule.PutRule;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class PutRuleVo extends PutRule {

    private String createName;

    private String typeName;

    private String typeColor = "red";


    public PutRule setType(String type){
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
