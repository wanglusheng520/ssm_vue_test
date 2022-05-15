package com.beneway.common.common.enums;

/**
 * 阶段枚举类
 */
public enum StageEnum {

    A_INVENTORY("A", "已入库"),

    B_FILLING_IN("B","填报中"),

    C_WAIT_EVALUATED("C","待评估"),

    D_EVALUATED("D","已评估"),

    Z_WAIT_RECTIFICATION("Z","待整改"),

    F_ALREADY_RECTIFICATION("F","已整改");


    private String stage;

    /**
     * 描述
     */
    private String describe;

    StageEnum(String stage, String describe) {
        if (stage == null){
            stage = "A";
        }
        this.stage = stage;
        this.describe = describe;
    }

    public String getStage() {
        return stage;
    }

    public String getDescribe() {
        return describe;
    }


}
