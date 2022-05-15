package com.beneway.common.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 填报标记
 */
public enum MarkEnum {

    YES("1", "已填报"),

    NO("0", "未填报");

    @EnumValue
    @JsonValue
    private String mark;

    /**
     * 描述
     */
    private String describe;

    MarkEnum(String mark, String describe) {
        if (mark == null){
            mark = "0";
        }
        this.mark = mark;
        this.describe = describe;
    }

    public String getMark() {
        return mark;
    }

    public String getDescribe() {
        return describe;
    }
}
