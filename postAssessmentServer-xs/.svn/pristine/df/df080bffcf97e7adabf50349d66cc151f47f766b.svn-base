package com.beneway.common.entity.prounitovertime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.beneway.common.common.enums.MarkEnum;
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
public class ProUnitOvertime {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String schemeId;

    /**
     * 0 未填报 1 已填报
     */
    private MarkEnum mark;

    /**
     * 截至时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date overTime;

    /**
     * 期数
     */
    private Integer period;

    private String year;


}
