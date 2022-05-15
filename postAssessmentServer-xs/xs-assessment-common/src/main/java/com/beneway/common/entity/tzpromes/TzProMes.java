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

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TzProMes {

    private String ID;

    /**
     * 上半年已完成累计投资
     */
    private String inv_lyear;

    /**
     * 历年累计实际完成开发
     */
    private String total_compmoney;

    /**
     * 本年累计完成
     */
    private String total_compmoneyLy;

    /**
     * 至去年计划完成
     */
    private String total_compmoney_ly_plan;
    /**
     * 本年预算中国家补助资金
     */
    private String yplan_gj_money;
    /**
     * 本年预算中省市补助资金
     */
    private String yplan_ss_money;
    /**
     * 本年预算中县财政资金
     */
    private String yplan_x_money;
    /**
     * 本年预算中银行带框
     */
    private String yplan_blank_money;
    /**
     * 本年预算中自筹资金
     */
    private String yplan_self_money;
    /**
     * 本年预算中中外资金
     */
    private String yplan_wz_money;
    /**
     * 项目编号
     */
    private String proj_code;
    /**
     * 项目名称
     */
    private String proj_name;
    /**
     * 项目类型
     */
    private String proj_type;
    /**
     * 负责人姓名
     */
    private String fzr_name;
    /**
     * 负责人电话
     */
    private String fzr_linkphone;
    /**
     * 项目性质
     */
    private String proj_xz;
    /**
     * 预计总投资金额
     */
    private String yj_money;
    /**
     * 拟建地址
     */
    private String nj_address;
    /**
     * 总建筑面积
     */
    private String total_mj;
    /**
     * 总用地边界图
     */
    private String total_map;
    /**
     * 建议书或者预工报告
     */
    private String xmjys_url;

    /**
     * 项目拟开工日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date xmnkg_data;
    /**
     * 分管领导
     */
    private String fg_lead;
    /**
     * 联系人
     */
    private String link_man;
    /**
     * 预计开工日期
     */
    private String exp_bdate;
    /**
     * 预计竣工日期
     */
    private String exp_edate;
    /**
     * 项目次序码
     */
    private String pro_order_code;
    /**
     * 年度计划状态
     */
    private String proplan_state;
    /**
     * 是否国有
     */
    private String is_stateinv;
    /**
     * 是否国有前期
     */
    private String is_stateinv_qq;
    /**
     * 是否重点项目
     */
    private String is_keycon;
    /**
     * 是否重大前期
     */
    private String is_majorpro;
    /**
     * 项目级别
     */
    private String pro_level;
    /**
     * 是否有主管部门
     */
    private String is_manage;
    /**
     * 申报时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String sb_date;
    /**
     * 重点工程类
     */
    private String keycon_type;
    /**
     * 项目分线
     */
    private String proj_fenx;

    /**
     * 责任单位id
     */
    private String proj_unitid;

    /**
     * 联系电话
     */

    private String link_phone;

    /**
     * 填报人
     */
    private String tb_man;

    /**
     * 填报人电话
     */
    private String tbman_phone;

    /**
     * 填报日期
     */
    private String tb_date;

    /**
     * 入库状态
     */
    private String rk_state;

    /**
     * 计划开工日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date plan_kgdate;

    /**
     * 开工日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date kg_date;

    /**
     * 当前进度
     */
    private String current_jd;

    /**
     * 初审日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cs_date;

    /**
     * 联审完成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ls_date;

    /**
     * 建设单位
     */
    private String jsdw;

    /**
     * 项目基本情况
     */
    private String proj_jbqk;

    /**
     * 投资企业情况
     */
    private String proj_tzqyqk;

    /**
     * 投资类型
     */
    private String proj_fl;

    /**
     * 国家补助资金
     */
    private String gjbz_money;

    /**
     * 省市补助资金
     */
    private String ssbz_money;

    /**
     * 县财政预算资金
     */
    private String xcz_money;

    /**
     * 银行贷款
     */
    private String yhdk_money;

    /**
     * 自筹
     */
    private String zc_money;

    /**
     * 外资
     */
    private String wz_money;

    /**
     * 其他资金
     */
    private String qt_money;

    /**
     * 项目赋码
     */
    private String proj_scode;

    /**
     * 新增建设用地面积
     */
    private String total_jsydmj;

    /**
     * 耕地
     */
    private String total_js_gdmj;

    /**
     * 林地
     */
    private String total_js_ldmj;

    /**
     * 园地
     */
    private String total_js_ydmj;

    /**
     * 海域面积
     */
    private String total_hymj;

    /**
     * 分管领导手机号码
     */
    private String fgLead_phone;

    /**
     * 多经自审情况
     */
    private String dgzs_info;

    /**
     * 建设规模
     */
    private String proj_jsgm;

    /**
     * 建设年限开始年
     */
    private String proj_jsbyear;

    /**
     * 建设年限结束年
     */
    private String proj_jseyear;

    /**
     * 国有投资类型
     */
    private String inv_type;

    /**
     * 填报年度
     */
    private String plan_year;

    /**
     * 上年形象建设内容
     */
    private String pb_lyear;

    /**
     * 本年预计完成投资
     */
    private String inv_cyear;

    /**
     * 本年预计达到形象进度
     */
    private String pb_cyear;

    /**
     * 转结或新安排标志
     */
    private String pro_phase;

    /**
     * 项目主管部门
     */
    private String pro_maindept;

    /**
     * 港口海岸线长度
     */
    private String total_sealong;

    /**
     * 本年预算中其他资金
     */
    private String yplan_otner_moiney;

    /**
     * 国有项目的归属部门
     */
    private String gygs_dept;

    /**
     * 是否是产业项目
     */
    private String l3_is_cy;

    /**
     * 是否是房地产项目
     */
    private String l3_is_fdc;

    /**
     * 是否省市县长项目
     */
    private String l3_is_xz;

    /**
     * 是否亚运配套
     */
    private String l3_yypt;

    /**
     * 是否海洋经济
     */
    private String l3_hyjj;

    /**
     * 是否省市重点
     */
    private String l3_szjj;

    /**
     * 设计单位
     */
    private String l3_sh_comp;

    /**
     * 设计单位联系人
     */
    private String l3_sj_linkman;

    /**
     * 设计单位电话
     */
    private String l3_sj_linkphone;

    /**
     * 施工单位
     */
    private String l3_sg_comp;

    /**
     * 施工单位联系人
     */
    private String l3_sg_linkman;

    /**
     * 施工单位联系电话
     */
    private String l3_sg_linkphone;

    /**
     * 监理单位
     */
    private String l3_jl_comp;

    /**
     * 监理单位联系人
     */
    private String l3_jl_linkman;

    /**
     * 监理单位联系电话
     */
    private String l3_jl_linkphone;

    /**
     * 实际开工
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date l3_kg_date;

    /**
     * 实际竣工
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date l3_jg_date;

    private String projId;

}
