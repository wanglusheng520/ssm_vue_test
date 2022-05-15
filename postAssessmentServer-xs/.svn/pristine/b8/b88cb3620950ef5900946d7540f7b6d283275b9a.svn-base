package com.beneway.common.entity.tzprojectearliermes;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.api.Assert;
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
 * @since 2022-03-23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TzProjectEarlierMes implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String ID;

    /**
     * 责任单位id
     */
    private String proj_unitid;

    /**
     * 计划开工日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date plan_kgdate;

    /**
     * 建议书或者预工报告
     */
    private String xmjys_url;

    /**
     * 项目拟开工时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date xmnkg_date;

    /**
     * 分管领导
     */
    private String fg_lead;

    /**
     * 联系人
     */
    private String link_man;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date tb_date;

    /**
     * 县财政预算资金
     */
    private String rk_state;

    /**
     * 开工日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date kg_date;

    /**
     * 记录状态
     */
    private String record_state;

    /**
     * 当前进度
     */
    private String current_jd;

    /**
     * 入跟踪库时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gzk_date;

    /**
     * 入前期库时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date qqk_date;

    /**
     * 入预备库时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ybk_date;

    /**
     * 入开工库时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date kgk_date;

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
     * 项目编号
     */
    private String proj_code;

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
     * 园林
     */
    private String total_js_ydmj;

    /**
     * 海域面积
     */
    private String total_hymj;

    /**
     * 分管领导手机号码
     */
    private String fg_lead_phone;

    /**
     * 多经自审情况
     */
    private String dgzs_info;

    /**
     * 规模建设
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
     * 陆域面积
     */
    private String total_lymj;

    /**
     * 港口海岸线长度
     */
    private String total_sealong;

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
     * 预计总投资额
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
     * 投资企业情况
     */
    private String proj_tzqyqk;

    /**
     * 投资类型
     */
    private String ls_yj;

    /**
     * 项目分线
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


}
