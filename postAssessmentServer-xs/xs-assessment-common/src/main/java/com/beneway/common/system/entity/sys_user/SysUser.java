package com.beneway.common.system.entity.sys_user;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser implements Serializable {

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    /**
     * 单位id
     */
    private Integer unitId;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer deleted;

    /**
     * 单位id
     */
    private Integer supUnitId;

    /**
     * 单位id
     */
    @TableField(exist = false)
    private Integer supAgencyId;
    /**
     * 单位id
     */
    @TableField(exist = false)
    private Integer agencyId;



}
