package com.beneway.common.entity.detailtarget.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.http.annotation.Contract;

/**
 * 指标分类
 */
public enum TypeEnum {

    NUMBER("1", "数字型"),
    CHOOSE("2", "选择型"),
    TEXT("3", "文本型"),
    FILES("4", "文件型"),
    FORMULA("5", "公式型");

    @EnumValue
    @JsonValue
    private String type;

    /**
     * 描述
     */
    private String describe;

    TypeEnum(String type, String describe) {
        this.type = type;
        this.describe = describe;
    }


    public String getType() {
        return type;
    }

    public String getDescribe() {
        return describe;
    }
}
