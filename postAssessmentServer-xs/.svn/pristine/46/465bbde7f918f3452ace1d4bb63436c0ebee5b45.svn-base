package com.beneway.common.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 比较结果枚举类
 */
public enum CompareEnum {

    LESS_THAN("-1","小于"),
    MORE_THAN("1","大于"),
    EQUAL("0","等于");

    @EnumValue
    @JsonValue
    private String result;

    private String content;

    public String getResult(){
        return result;
    }

    public String getContent(){
        return content;
    }

    CompareEnum(String result,String content){
        this.result = result;
        this.content = content;
    }
}
