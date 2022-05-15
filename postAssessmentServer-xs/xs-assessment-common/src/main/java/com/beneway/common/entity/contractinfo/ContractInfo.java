package com.beneway.common.entity.contractinfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("contract_info")
public class ContractInfo {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 合同名称
     */
    private String contractName;

    /**
     * 合同类型
     */
    private String contractType;

    /**
     * 合同属性
     */
    private String contractAttr;

    /**
     * 主体单位
     */
    private String applyUnit;

    /**
     * 申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyDate;

    /**
     * 负责人
     */
    private String fuzeren;
    /**
     * 合同向对方
     */
    private String contractobj;

    /**
     * 机构代码
     */
    private String agencyno;

    /**
     * 联系人
     */
    private String lianxiren;

    /**
     * 联系电话
     */
    private String lianxidianhua;

    /**
     * 签订地
     */
    private String qiandingdi;

    /**
     * 标的
     */
    private String biaodi;

    /**
     * 标的额
     */
    private String biaodie;

    /**
     * 合同编号
     */
    private String contractnumber;

    /**
     * 承办单位
     */
    private String bearunit;

    /**
     * 描述
     */
    private String description;

    /**
     * 删除标记
     */
    private int isDelete;

    /**
     * 状态
     */
    private String contractStatus;

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
}

