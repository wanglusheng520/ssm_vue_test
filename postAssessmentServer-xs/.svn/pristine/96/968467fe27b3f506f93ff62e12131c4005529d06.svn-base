package com.beneway.common.entity.majorproject.vo;

import com.beneway.common.entity.majorproject.MajorProject;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class MajorProjectVo extends MajorProject {

    private String earlyWarning;

    private String isExpect;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;

    private String overdue;
}
