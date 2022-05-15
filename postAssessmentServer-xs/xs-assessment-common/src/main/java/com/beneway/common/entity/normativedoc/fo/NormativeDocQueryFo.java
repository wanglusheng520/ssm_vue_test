package com.beneway.common.entity.normativedoc.fo;

import com.beneway.common.common.enums.StatusEnum;
import com.beneway.common.common.utils.page.PageQuery;
import lombok.Data;

@Data
public class NormativeDocQueryFo extends PageQuery {

    private StatusEnum statusEnum;

    private String title;

    private String normativeStatus;

    private String normativeStage;

    private Integer unitId;

    //填报状态
    private String fillStatus;
    //填报阶段
    private String logo;

}
