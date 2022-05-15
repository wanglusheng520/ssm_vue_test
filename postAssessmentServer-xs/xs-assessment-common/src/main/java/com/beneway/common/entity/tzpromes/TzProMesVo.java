package com.beneway.common.entity.tzpromes;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class TzProMesVo {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 上半年已完成累计投资
     */
    private String invLyear;

    /**
     * 历年累计实际完成开发
     */
    private String totalCompmoney;

    /**
     * 本年累计完成
     */
    private String totalCompmoneyLy;

    /**
     * 至去年计划完成
     */
    private String totalCompmoneyLPlan;
    /**
     * 本年预算中国家补助资金
     */
    private String yplanGjMoney;
    /**
     * 本年预算中省市补助资金
     */
    private String yplanSsMoney;
    /**
     * 本年预算中县财政资金
     */
    private String yplanXMoney;
    /**
     * 本年预算中银行带框
     */
    private String yplanBlankMoney;
    /**
     * 本年预算中自筹资金
     */
    private String yplanSelfMoney;
    /**
     * 本年预算中中外资金
     */
    private String yplanWzMoney;
    /**
     * 项目编号
     */
    private String projCode;
    /**
     * 项目名称
     */
    private String projName;
    /**
     * 项目类型
     */
    private String projType;
    /**
     * 负责人姓名
     */
    private String fzrName;
    /**
     * 负责人电话
     */
    private String fzrLinkphone;
    /**
     * 项目性质
     */
    private String projXz;
    /**
     * 预计总投资金额
     */
    private String yjMoney;
    /**
     * 拟建地址
     */
    private String njAddress;
    /**
     * 总建筑面积
     */
    private String totalMj;
    /**
     * 总用地边界图
     */
    private String totalMap;
    /**
     * 建议书或者预工报告
     */
    private String xmjysUrl;

    /**
     * 项目拟开工日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date xmnkgData;
    /**
     * 分管领导
     */
    private String fgLead;
    /**
     * 联系人
     */
    private String linkMan;
    /**
     * 预计开工日期
     */
    private String expBdate;
    /**
     * 预计竣工日期
     */
    private String expEdate;
    /**
     * 项目次序码
     */
    private String proOrderCode;
    /**
     * 年度计划状态
     */
    private String proplanState;
    /**
     * 是否国有
     */
    private String isStateinv;
    /**
     * 是否国有前期
     */
    private String isStateinvQq;
    /**
     * 是否重点项目
     */
    private String isKeycon;
    /**
     * 是否重大前期
     */
    private String isMajorpro;
    /**
     * 项目级别
     */
    private String proLevel;
    /**
     * 是否有主管部门
     */
    private String isManage;
    /**
     * 申报时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String sbDate;
    /**
     * 重点工程类
     */
    private String keyconType;
    /**
     * 项目分线
     */
    private String projFenx;

    /**
     * 责任单位id
     */
    private String projUnitid;

    /**
     * 联系电话
     */
    private String linkPhone;

    /**
     * 填报人
     */
    private String tbMan;

    /**
     * 填报人电话
     */
    private String tbmanPhone;

    /**
     * 填报日期
     */
    private String tbDate;

    /**
     * 入库状态
     */
    private String rkState;

    /**
     * 计划开工日期
     */
    private Date planKgdate;

    /**
     * 开工日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date kgDate;

    /**
     * 当前进度
     */
    private String currentJd;

    /**
     * 初审日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date csDate;

    /**
     * 联审完成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lsDate;

    /**
     * 建设单位
     */
    private String jsdw;

    /**
     * 项目基本情况
     */
    private String projJbqk;

    /**
     * 投资企业情况
     */
    private String projTzqyqk;

    /**
     * 投资类型
     */
    private String projFl;

    /**
     * 国家补助资金
     */
    private String gjbzMoney;

    /**
     * 省市补助资金
     */
    private String ssbzMoney;

    /**
     * 县财政预算资金
     */
    private String xczMoney;

    /**
     * 银行贷款
     */
    private String yhdkMoney;

    /**
     * 自筹
     */
    private String zcMoney;

    /**
     * 外资
     */
    private String wzMoney;

    /**
     * 其他资金
     */
    private String qtMoney;

    /**
     * 项目赋码
     */
    private String projScode;

    /**
     * 新增建设用地面积
     */
    private String totalJsydmj;

    /**
     * 耕地
     */
    private String totalJsGdmj;

    /**
     * 林地
     */
    private String totalJsLdmj;

    /**
     * 园地
     */
    private String totalJsYdmj;

    /**
     * 海域面积
     */
    private String totalHymj;

    /**
     * 分管领导手机号码
     */
    private String fgLeadPhone;

    /**
     * 多经自审情况
     */
    private String dgzsInfo;

    /**
     * 建设规模
     */
    private String projJsgm;

    /**
     * 建设年限开始年
     */
    private String projJsbyear;

    /**
     * 建设年限结束年
     */
    private String projJseyear;

    /**
     * 国有投资类型
     */
    private String invType;

    /**
     * 填报年度
     */
    private String planYear;

    /**
     * 上年形象建设内容
     */
    private String pbLyear;

    /**
     * 本年预计完成投资
     */
    private String invCyear;

    /**
     * 本年预计达到形象进度
     */
    private String pbCyear;

    /**
     * 转结或新安排标志
     */
    private String proPhase;

    /**
     * 项目主管部门
     */
    private String proMaindept;

    /**
     * 港口海岸线长度
     */
    private String totalSealong;

    /**
     * 本年预算中其他资金
     */
    private String yplanOtnerMoiney;

    /**
     * 国有项目的归属部门
     */
    private String gygsDept;

    /**
     * 是否是产业项目
     */
    private String l3IsCy;

    /**
     * 是否是房地产项目
     */
    private String l3IsFdc;

    /**
     * 是否省市县长项目
     */
    private String l3IsXz;

    /**
     * 是否亚运配套
     */
    private String l3Yypt;

    /**
     * 是否海洋经济
     */
    private String l3Hyjj;

    /**
     * 是否省市重点
     */
    private String l3Szjj;

    /**
     * 设计单位
     */
    private String l3ShComp;

    /**
     * 设计单位联系人
     */
    private String l3SjLinkman;

    /**
     * 设计单位电话
     */
    private String l3SjLinkphone;

    /**
     * 施工单位
     */
    private String l3SgComp;

    /**
     * 施工单位联系人
     */
    private String l3SgLinkman;

    /**
     * 施工单位联系电话
     */
    private String l3SgLinkphone;

    /**
     * 监理单位
     */
    private String l3JlComp;

    /**
     * 监理单位联系人
     */
    private String l3JlLinkman;

    /**
     * 监理单位联系电话
     */
    private String l3JlLinkphone;

    /**
     * 实际开工
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date l3KgDate;

    /**
     * 实际竣工
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date l3JgDate;

}
