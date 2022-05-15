package com.beneway.common.entity.detailtarget;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.beneway.common.entity.choiceindicator.ChoiceIndicator;
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
public class DetailTarget {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String majorIndicatorsId;

    private String detailTargetName;

    private String type;

    private String promptMessage;

    private String expect;

    private String operator;

    private String field;

    private String name;

    private Double seq;

    @TableLogic
    private Integer isDel;

}
