package com.beneway.common.common.enums;

/**
 * @email 2434017367@qq.com
 * @author: zhy
 * @date: 2021/7/28
 * @time: 21:01
 *
 * 单位类型
 */
public enum AgencyPositionEnum {

    /**
     * 普通单位
     */
    PT(1),
    /**
     * 司法局
     */
    SFJ(2),
    /**
     * 县政府
     */
    XZF(3),
    /**
     * 县委
     */
    XW(4),
    /**
     * 市场监管局
     */
    SCJGJ(5),
    /**
     * 县人民政府
     */
    GOV(6),
    /**
     * 政府机构
     */
    ZFJG(7);

    /**
     * 类型
     */
    private Integer position;

    private AgencyPositionEnum(Integer type){
        this.position = type;
    }

    public Integer getPosition() {
        return position;
    }

    public static AgencyPositionEnum getByPosition(Integer position){
        AgencyPositionEnum[] values = values();
        for (AgencyPositionEnum agencyPositionEnum : values) {
            if (agencyPositionEnum.getPosition().equals(position)){
                return agencyPositionEnum;
            }
        }
        return null;
    }
}
