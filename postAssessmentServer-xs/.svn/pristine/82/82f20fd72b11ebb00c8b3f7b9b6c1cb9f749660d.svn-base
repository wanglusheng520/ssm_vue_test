package com.beneway.common.entity.scheme.fo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.beneway.common.entity.detailtarget.DetailTarget;
import com.beneway.common.entity.detailtarget.fo.DetailTargetFo;
import com.beneway.common.entity.scheme.Scheme;
import com.beneway.common.entity.schemeagencytarget.vo.SchemeAgencyTargetVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class SchemeFo extends Scheme {

    /**
     * 填报单位
     */
    private Integer agencyId;
    /**
     * 周期
     */
    private String period;

    /**
     * 起始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private List<Date> date;

    @TableField(exist = false)
    private List<DetailTargetFo> detailTargets;

    @TableField(exist = false)
    private List<SchemeAgencyTargetVo> schemeAgencyTargets;

}
