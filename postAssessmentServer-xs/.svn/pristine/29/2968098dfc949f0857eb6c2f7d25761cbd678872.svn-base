package com.beneway.common.entity.tzplan;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TzPlan {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 项目id
     */
    private String projId;

    /**
     * 项目名称
     */
    private String projName;

    /**
     * 年度投资
     */
    private String annInvest;

    /**
     * 状态
     */
    private String state;

    /**
     * 审核意见
     */
    private String auditDes;

    /**
     * 年度
     */
    private String year;

    /**
     * 年度实际投资
     */
    private String annInvestActual;

}
