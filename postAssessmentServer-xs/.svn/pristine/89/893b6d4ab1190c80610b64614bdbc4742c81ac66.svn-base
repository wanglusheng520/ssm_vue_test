package com.beneway.common.entity.majorindicators;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.beneway.common.entity.detailtarget.DetailTarget;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.models.auth.In;
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
public class MajorIndicators {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    private String targetTitle;

    private String targetRemark;

    private String type;

    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 一级分类
     */
    private String typeFirst;
    /**
     * 二级分类
     */
    private String typeSecond;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer isDel;

}
