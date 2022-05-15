package com.beneway.common.entity.majorproject.fo;

import com.beneway.common.common.enums.StatusEnum;
import com.beneway.common.common.utils.page.PageQuery;
import com.beneway.common.entity.satanswer.SatAnswer;
import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class MajorProjectQueryFo extends PageQuery {

    private StatusEnum statusEnum;

    private String projectName;

    private String investType;

    private String projectType;

    private String buildNature;

    private String projectStatus;

    private Integer unitId;

    private String projectStage;

    //填报状态
    private String fillStatus;
    //填报阶段
    private String logo;


    /**
     * 用户牵头单位关联查询
     */
    private String investTypes;

    private String projectTypes;

}
