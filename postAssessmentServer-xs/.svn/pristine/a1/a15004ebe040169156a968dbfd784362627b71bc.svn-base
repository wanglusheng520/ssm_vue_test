package com.beneway.common.earlywarn.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

/**
 * 预警类型枚举类
 */
public enum WarnTypeEnum {

    OVERDUE("Y" , "逾期"),
    NEAR_OVERDUE("O" , "临近逾期"),
    NO_STANDARD("N" , "指标未达标");

    @EnumValue
    @JsonValue
    private String type;

    private String content;

    WarnTypeEnum(String type,String content){
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}
