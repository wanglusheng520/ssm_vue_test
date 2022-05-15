package com.beneway.common.entity.operationlog;


import com.baomidou.mybatisplus.annotation.TableField;
import com.beneway.common.entity.majorproject.MajorProject;
import com.beneway.common.entity.normativedoc.NormativeDoc;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OperationLog {

    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String assId;

    private String assType;

    private String stage;

    @TableField(exist = false)
    private MajorProject majorProject;
    @TableField(exist = false)
    private NormativeDoc normativeDoc;

}
