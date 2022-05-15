package com.beneway.common.common.enums;

/**
 * @email 2434017367@qq.com
 * @author: zhy
 * @date: 2021/7/28
 * @time: 20:47
 *
 * 用户角色职位枚举
 */
public enum UserPositionEnum {

    /*
        公共
     */
    /**
     * 普通人员
     */
    PTRY(1),
    /**
     * 管理员
     */
    GLY(2),
    /**
     * 对外人员
     */
    DWRY(3),
    /**
     * 公平竞争审查人员
     */
    GPJZSCRY(4),
    /**
     * 合法性审查人员
     */
    HFXSCRY(5),
    /**
     * 领导
     */
    LD(6),
    /**
     * 县政府法制人员
     */
    XZFFZRY(7),
    /**
     * 主要领导
     */
    ZYLD(8),
    /**
     * 公平竞争审查分管领导
     */
    GPJZSCFGLD(9),
    /**
     * 合法性审查分管领导
     */
    HFXSCFGLD(10),
    /**
     * 分管主任
     */
    XFBFGZR(11),
    /**
     * 副县长
     */
    FXZ(12);

    private Integer position;

    private UserPositionEnum(Integer position){
        this.position = position;
    }

    public Integer getPosition() {
        return position;
    }

    public static UserPositionEnum getByPosition(Integer position){
        UserPositionEnum[] values = values();
        for (UserPositionEnum value : values) {
            if (value.getPosition().equals(position)){
                return value;
            }
        }
        throw new NullPointerException("暂无此人员");
    }

}
