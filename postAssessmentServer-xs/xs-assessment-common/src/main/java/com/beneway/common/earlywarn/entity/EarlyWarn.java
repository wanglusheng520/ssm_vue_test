package com.beneway.common.earlywarn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.beneway.common.earlywarn.entity.enums.WarnTypeEnum;
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

/**
 * 预警表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EarlyWarn {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String assId;

    private String assType;

    private WarnTypeEnum warnType;

    /**
     * 1消除预警
     */
    private String warnMark;

    private int count;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(exist = false)
    private MajorProject majorProject;
    @TableField(exist = false)
    private NormativeDoc normativeDoc;
}
