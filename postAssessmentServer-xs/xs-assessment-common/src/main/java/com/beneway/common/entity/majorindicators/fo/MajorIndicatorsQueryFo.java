package com.beneway.common.entity.majorindicators.fo;

import com.beneway.common.common.utils.page.PageQuery;
import com.beneway.common.entity.detailtarget.DetailTarget;
import lombok.Data;

@Data
public class MajorIndicatorsQueryFo extends PageQuery {

    private String type;

    private String typeFirst;

}
