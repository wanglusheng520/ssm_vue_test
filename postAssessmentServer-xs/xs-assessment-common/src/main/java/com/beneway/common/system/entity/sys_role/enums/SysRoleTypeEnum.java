package com.beneway.common.system.entity.sys_role.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;


public enum SysRoleTypeEnum {

    /**
     * 用户
     */
    USER("U"),

    /**
     * 范围
     */
    SCOPE("S");

    @EnumValue
    @JsonValue
    private final String type;

    SysRoleTypeEnum(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static SysRoleTypeEnum getByKey(String key){
        SysRoleTypeEnum[] values = values();
        for (SysRoleTypeEnum value : values) {
            if (value.getType().equals(key)){
                return value;
            }
        }
        return null;
    }

}
