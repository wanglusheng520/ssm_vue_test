package com.beneway.common.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.beneway.common.common.utils.jwt.LoginUserUtils;
import com.beneway.common.entity.majorproject.fo.MajorProjectQueryFo;
import com.beneway.common.entity.normativedoc.fo.NormativeDocQueryFo;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

/**
 * 状态枚举类
 */
public enum StatusEnum {

    //决策库
    YRK("A","Y","",true,"","qt"),

    //待评估
    MAKE_PLAN("B","Y",StageEnum.C_WAIT_EVALUATED.getStage(),true,"","pg"),

    //已评估
    EVALUATED("C","Y",StageEnum.D_EVALUATED.getStage(),true,"","pg"),

    //待填报
    WAIT_FILL("D","Y","",true,MarkEnum.NO.getMark(),"tb"),

    //已填报
    ALREADY_FILL("E","Y","",true,MarkEnum.YES.getMark(),"tb"),

    //待整改
    WAIT_RECTIFICATION("F","Y",StageEnum.Z_WAIT_RECTIFICATION.getStage(),true,MarkEnum.NO.getMark(), "zg"),

    //已整改
    ALREADY_RECTIFICATION("G","Y",StageEnum.F_ALREADY_RECTIFICATION.getStage(),true,MarkEnum.YES.getMark(),"zg");

    @EnumValue
    @JsonValue
    private final String type;
    //状态
    private final String status;
    //流程阶段
    private final String stage;
    //菜单标记
    private final Boolean flag;
    //填报状态
    private final String fillStatus;
    //填报标记
    private final String logo;

    StatusEnum(String type,String status,String stage,Boolean flag,String fillStatus,String logo) {
        this.type = type;
        this.status = status;
        this.stage = stage;
        this.flag = flag;
        this.fillStatus = fillStatus;
        this.logo = logo;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getStage() {
        return stage;
    }

    public Boolean getFlag(){
        return flag;
    }

    public String getFillStatus(){
        return fillStatus;
    }

    public String getLogo(){
        return logo;
    }

    /**
     * 设置项目分页条件
     */
    public static void setStatusProjectQuery(MajorProjectQueryFo majorProjectQueryFo,StatusEnum statusEnum){
        majorProjectQueryFo.setProjectStatus(statusEnum.getStatus());
        if(StringUtils.isEmpty(majorProjectQueryFo.getProjectStage())){
            majorProjectQueryFo.setProjectStage(statusEnum.getStage());
        }
        if(statusEnum.getFlag()){
            majorProjectQueryFo.setLogo(statusEnum.getLogo());
            majorProjectQueryFo.setUnitId(LoginUserUtils.getLoginUserInfo().getSupUnitId());
            majorProjectQueryFo.setFillStatus(statusEnum.getFillStatus());
        }
    }

    /**
     * 设置规范性文件分页条件
     */
    public static void setStatusNormativeDocQuery(NormativeDocQueryFo normativeDocQueryFo,StatusEnum statusEnum){
        normativeDocQueryFo.setNormativeStatus(statusEnum.getStatus());
        if(StringUtils.isEmpty(normativeDocQueryFo.getNormativeStage())){
            normativeDocQueryFo.setNormativeStage(statusEnum.getStage());
        }
        if(statusEnum.getFlag()){
            normativeDocQueryFo.setLogo(statusEnum.getLogo());
            normativeDocQueryFo.setUnitId(LoginUserUtils.getLoginUserInfo().getSupUnitId());
            normativeDocQueryFo.setFillStatus(statusEnum.getFillStatus());
        }
    }



}
