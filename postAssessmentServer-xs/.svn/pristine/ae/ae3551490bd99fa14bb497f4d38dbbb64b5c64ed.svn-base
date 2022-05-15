package com.beneway.common.entity.assessstart;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.beneway.common.entity.files.Files;
import com.beneway.common.entity.prounitovertime.ProUnitOvertime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AssessStart {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    private String assId;

    private Integer qtUnit;

    private String xtUnit;

    private String assessExplain;

    private String files;

    private String assType;

    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(exist = false)
    private String agencyName;

}
