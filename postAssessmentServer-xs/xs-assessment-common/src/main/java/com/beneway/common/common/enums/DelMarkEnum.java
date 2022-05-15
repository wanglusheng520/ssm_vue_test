package com.beneway.common.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 删除标记
 */
public enum DelMarkEnum {
    YES(1, "已删除"),

    NO(0, "未删除");

    @EnumValue
    @JsonValue
    private Integer mark;

    /**
     * 描述
     */
    private String describe;

    DelMarkEnum(Integer mark, String describe) {
        this.mark = mark;
        this.describe = describe;
    }

    public int getMark() {
        return mark;
    }

    public String getDescribe() {
        return describe;
    }
}
