package com.beneway.common.entity.putrule;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class PutRule {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String content;

    private String type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String createUser;

    private String ruleName;

    private String pid;

    private String k;

    private String v;

    @TableField(exist = false)
    private List<PutRule> putRules;

    @TableField(exist = false)
    private String userName;

    private String symbol;

    private Boolean isWork;

    private String val;

    @TableField(exist = false)
    private List<String> vs;

}
