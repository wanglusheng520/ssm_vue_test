package com.beneway.common.entity.majorproject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.beneway.common.base.entity.BaseBean;
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
public class MajorProject extends BaseBean {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 投资类型
     */
    private String investType;

    /**
     * 项目编号
     */
    private String projectNb;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目类型
     */
    private String projectType;

    /**
     * 责任单位
     */
    private String resUnit;

    /**
     * 项目分线
     */
    private String projectSubLine;

    /**
     * 投资金额
     */
    private String  projectAmount;

    /**
     * 建设年限
     */
    private String buildYears;

    /**
     * 建设地址
     */
    private String buildAddress;

    /**
     * 建设单位
     */
    private String buildUnit;

    /**
     * 建设联系人
     */
    private String contact;

    /**
     * 建设单位联系电话
     */
    private String contactTel;

    /**
     * 建设规模
     */
    private String buildScale;

    /**
     * 建设性质
     */
    private String buildNature;

    /**
     * 分管领导
     */
    private String chargeLeader;

    /**
     * 项目联系人
     */
    private String projectContact;

    /**
     * 项目联系电话
     */
    private String projectContactTel;

    /**
     * 删除标记
     */
    private int isDelete;

    /**
     * 状态
     */
    private String projectStatus;

    /**
     * 研判等级
     */
    private String projectGrade;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 流程状态
     */
    private String projectStage;
}
