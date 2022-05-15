package com.beneway.common.entity.system.agency;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.beneway.common.dao.detailtarget.DetailTargetDao;
import com.beneway.common.entity.detailtarget.DetailTarget;
import com.beneway.common.entity.system.login.Loginuser;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@TableName("agency")
public class Agency implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer pid;
    /**
     * 单位名
     */
    private String agencyName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String createMan;
    /**
     * 单位简称
     */
    private String agencyShort;
    /**
     * 单位编码
     */
    private String agencyCode;
    /**
     * 排序号
     */
    private String seq;
    /**
     * 类型 1：区域，2：单位 3：科室
     */
    private String agencyType;
    /**
     * 系统名称
     */
    private String systemName;
    /**
     * 系统编号
     */
    private String systemCode;
    /**
     * 单位类型定位
     */
    private Integer position;
    /**
     * 司法所id
     */
    private Integer sfAgencyId;
    /**
     * 分组
     */
    private String agencyGrouping;
    /**
     * 是否刪除 0：否 1：是
     */
    private Boolean del;


    @TableField(exist = false)
    private List<Agency> children;

    @TableField(exist = false)
    private List<Loginuser> userList;

    public void setChildrenAgency(Agency agency){
        if (children == null){
            children = new ArrayList<>();
        }
        children.add(agency);
    }

    @TableField(exist = false)
    private String majorProjectId;
    @TableField(exist = false)
    private List<DetailTarget> detailTargets;
    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<Date> data;
    @TableField(exist = false)
    private String period;
}
