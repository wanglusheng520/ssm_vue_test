package com.beneway.common.entity.system.login;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.beneway.common.entity.system.role.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@TableName("loginuser")
public class Loginuser implements Serializable {
    /**
     *
     */
    private String id;
    /**
     * 用户名字
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 账号
     */
    private String account;
    /**
     * 科室id
     */
    private Integer agencyId;
    /**
     * 单位id
     */
    private Integer supAgencyId;
    /**
     * 创建人
     */
    private String createMan;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 修改人
     */
    private String updateMan;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    /**
     * 手机号
     */
    private String phone;

    /**
     * 钉钉用户id
     */
    private String accountId;

    /**
     * 钉钉租户id
     */
    private String tenantId;
    /**
     * 是否已经绑定，0否，1是
     */
    private String bind;

    /**
     * 职称id
     */
    private String titleId;

    @TableField(exist = false)
    private String titleName;

    /**
     * 是否刪除 0：否 1：是
     */
    private Boolean del;

    /**
     * 部门名称
     */
    @TableField(exist = false)
    private String agencyName;

    /**
     * 科室名称
     */
    @TableField(exist = false)
    private String deptName;

    @TableField(exist = false)
    private Integer agencyPosition;

    @TableField(exist = false)
    private List<Integer> positionList;

    @TableField(exist = false)
    private List<Integer> roleIdList;

    @TableField(exist = false)
    private List<Role> roleList;

}
