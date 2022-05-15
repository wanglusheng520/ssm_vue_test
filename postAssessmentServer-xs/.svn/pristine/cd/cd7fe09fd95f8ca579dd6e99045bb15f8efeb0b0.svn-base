package com.beneway.common.common.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.beneway.common.service.majorproject.MajorProjectServiceImpl;
import com.beneway.common.service.normativedoc.NormativeDocServiceImp;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AssessmentTypeEnums {


    PROJECT("P", "投资项目", "blue", MajorProjectServiceImpl.class),
    NORMATIVE_DOC("N","规范性文件","green", NormativeDocServiceImp.class);

    @EnumValue
    @JsonValue
    private String type;

    private String describe;

    private String color;
    /**
     * 处理类
     */
    private final Class disposeClass;


    public String getType() {
        return type;
    }

    public String getDescribe() {
        return describe;
    }

    public String getColor() {
        return color;
    }

    public Class getDisposeClass() {
        return disposeClass;
    }


    AssessmentTypeEnums(String type, String describe, String color,Class disposeClass) {
        this.type = type;
        this.describe = describe;
        this.color = color;
        this.disposeClass = disposeClass;
    }

    public static AssessmentTypeEnums getByType(String type){
        AssessmentTypeEnums[] values = values();
        for (AssessmentTypeEnums assessmentTypeEnums : values) {
            if (assessmentTypeEnums.getType().equals(type)){
                return assessmentTypeEnums;
            }
        }
        return null;
    }
}
