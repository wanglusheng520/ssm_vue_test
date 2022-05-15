package com.beneway.common.entity.tzsjmes;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wj
 * @since 2022-03-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TzSjMes implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 主表id
     */
    private String mainId;

    /**
     * 月份
     */
    private String mMonth;

    /**
     * 计划完成投资
     */
    private String planInvest;

    /**
     * 累计计划完成投资
     */
    private String planInvestTotal;

    /**
     * 主要节点形象进度
     */
    private String nodeProgress;

    /**
     * 形象进度计划完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date nodeProTime;

    /**
     * 实际完成投资
     */
    private String planInvestActual;

    /**
     * 累计实际完成投资
     */
    private String planInvestTotalActual;

    /**
     * 形象进度实际完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date nodeProTimeActual;

    /**
     * 审批状态
     */
    private String state;

    /**
     * 实际完成形象进度
     */
    private String nodeRealProgress;

    /**
     * 审批意见
     */
    private String auditDes;

    private String projId;


}
