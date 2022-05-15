package com.beneway.common.service.scheme;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateBetween;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.base.BaseMainService;
import com.beneway.common.base.entity.BaseMain;
import com.beneway.common.common.enums.*;
import com.beneway.common.common.rabbitMQ.provider.ProviderUtils;
import com.beneway.common.common.rabbitMQ.provider.RabbitMQConstant;
import com.beneway.common.common.result.Result;
import com.beneway.common.common.utils.ClassUtil;
import com.beneway.common.common.utils.exception.RRException;
import com.beneway.common.common.utils.jwt.LoginUserUtils;
import com.beneway.common.dao.prounitovertime.ProUnitOvertimeDao;
import com.beneway.common.dao.scheme.SchemeDao;
import com.beneway.common.entity.answerrecord.AnswerRecord;
import com.beneway.common.entity.answerrecord.AnswerRecordVo;
import com.beneway.common.entity.assuser.AssUser;
import com.beneway.common.entity.choiceindicator.ChoiceIndicator;
import com.beneway.common.entity.detailtarget.DetailTarget;
import com.beneway.common.entity.detailtarget.vo.DetailTargetVo;
import com.beneway.common.entity.detailtargetitem.DetailTargetItem;
import com.beneway.common.entity.detailtargetitem.vo.DetailTargetItemVo;
import com.beneway.common.entity.detailtargetitemoperator.DetailTargetItemOperator;
import com.beneway.common.entity.files.Files;
import com.beneway.common.entity.majorindicators.MajorIndicators;
import com.beneway.common.entity.majorindicators.vo.MajorIndicatorsVo;
import com.beneway.common.entity.majorproject.MajorProject;
import com.beneway.common.entity.majorproject.vo.MajorProjectVo;
import com.beneway.common.entity.prounitovertime.ProUnitOvertime;
import com.beneway.common.entity.satanswer.SatAnswer;
import com.beneway.common.entity.satanswer.vo.SatAnswerVo;
import com.beneway.common.entity.scheme.Scheme;
import com.beneway.common.entity.scheme.fo.SchemeFo;
import com.beneway.common.entity.scheme.vo.SchemeListFo;
import com.beneway.common.entity.scheme.vo.SchemeVo;
import com.beneway.common.entity.schemeagency.SchemeAgency;
import com.beneway.common.entity.schemeagency.vo.SchemeAgencyVo;
import com.beneway.common.entity.schemeagencytarget.SchemeAgencyTarget;
import com.beneway.common.entity.schemeagencytarget.vo.SchemeAgencyTargetVo;
import com.beneway.common.entity.schemetarget.SchemeTarget;
import com.beneway.common.entity.schemetarget.vo.SchemeTargetVo;
import com.beneway.common.entity.schemetargethistoryanswer.SchemeTargetHistoryAnswer;
import com.beneway.common.service.answerrecord.AnswerRecordService;
import com.beneway.common.service.assessstart.AssessStartService;
import com.beneway.common.service.assuser.AssUserService;
import com.beneway.common.service.choiceindicator.ChoiceIndicatorService;
import com.beneway.common.service.detailtarget.DetailTargetService;
import com.beneway.common.service.detailtargetitem.DetailTargetItemService;
import com.beneway.common.service.detailtargetitemoperator.DetailTargetItemOperatorService;
import com.beneway.common.service.files.FilesService;
import com.beneway.common.service.majorindicators.MajorIndicatorsService;
import com.beneway.common.service.majorproject.MajorProjectService;
import com.beneway.common.service.prounitovertime.ProUnitOvertimeService;
import com.beneway.common.service.satanswer.SatAnswerService;
import com.beneway.common.service.schemeagency.SchemeAgencyService;
import com.beneway.common.service.schemeagencytarget.SchemeAgencyTargetService;
import com.beneway.common.service.schemetarget.SchemeTargetService;
import com.beneway.common.service.schemetargethistoryanswer.SchemeTargetHistoryAnswerService;
import com.beneway.common.system.entity.sys_unit.vo.SysUnitComVo;
import com.beneway.common.system.service.sys_unit.SysUnitService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SchemeServiceImpl extends ServiceImpl<SchemeDao, Scheme> implements SchemeService {

    @Autowired
    private SchemeDao schemeDao;
    @Autowired
    private SchemeAgencyService schemeAgencyService;
    @Autowired
    private SchemeAgencyTargetService schemeAgencyTargetService;
    @Autowired
    private MajorProjectService majorProjectService;
    @Autowired
    private SatAnswerService satAnswerService;
    @Autowired
    private SchemeTargetHistoryAnswerService schemeTargetHistoryAnswerService;
    @Autowired
    private DetailTargetItemOperatorService detailTargetItemOperatorService;
    @Autowired
    private FilesService filesService;
    @Autowired
    private AnswerRecordService answerRecordService;
    @Autowired
    private AssessStartService assessStartService;
    @Autowired
    private SysUnitService sysUnitService;
    @Autowired
    private SchemeTargetService schemeTargetService;
    @Autowired
    private DetailTargetService detailTargetService;
    @Autowired
    private MajorIndicatorsService majorIndicatorsService;
    @Autowired
    private ChoiceIndicatorService choiceIndicatorService;
    @Autowired
    private DetailTargetItemService detailTargetItemService;
    @Autowired
    private ProUnitOvertimeService proUnitOvertimeService;
    @Autowired
    private ProUnitOvertimeDao proUnitOvertimeDao;
    @Autowired
    private BaseMainService baseMainService;
    @Autowired
    private AssUserService assUserService;



    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result addScheme(SchemeListFo schemeListFo) {

        //保存方案scheme
        Scheme scheme = ClassUtil.toClass(schemeListFo,Scheme.class);
        scheme.setCreateTime(new Date())
                .setCreateUser(LoginUserUtils.getLoginUserId());
        schemeDao.insert(scheme);

        //保存截至日期
        proUnitOvertimeService.saveList(schemeListFo.getProUnitOvertimeList(),scheme);

        //保存明细scheme_target
        schemeTargetService.saveList(schemeListFo.getDetailTargetFos(),scheme);

        //保存方案单位scheme_agency
        String xtUnit = schemeAgencyService.saveList(schemeListFo.getDetailTargetFos(),scheme);

        //添加启动记录
        assessStartService.saveMakePlan(scheme,xtUnit);

        //更新主表
        BaseMain baseMain = new BaseMain(schemeListFo.getAssType(),schemeListFo.getAssId());
        baseMainService.updateMain(baseMain,StageEnum.C_WAIT_EVALUATED);
//        AssessmentTypeEnums assessmentTypeEnums = AssessmentTypeEnums.getByType(schemeListFo.getAssType());
//        BaseService baseService = getMatterService(assessmentTypeEnums);
//        baseService.updateStage(StageEnum.C_WAIT_EVALUATED,schemeListFo.getAssId());

        //给协同单位发信息发信息
        Map<String, Object> Mess = new HashMap<>();
        Mess.put("agencyIds" , xtUnit);
        Mess.put("stage" , MessEnum.ZDFA.getStage());
        Mess.put("assId" , schemeListFo.getAssId());
        Mess.put("assType" , schemeListFo.getAssType());
        ProviderUtils.providerAdd(RabbitMQConstant.EXCHANGE_TOPIC_TB, RabbitMQConstant.ROUTING_KEY_TOPIC_MESS, Mess);

        //保存相关人员
        List<AssUser> assUsers = new ArrayList<>();
        String[] xtIds = xtUnit.split(",");
        Integer supUnitId = LoginUserUtils.getSupUnitId();
        for (String id : xtIds) {
            assUsers.add(AssUser.builder()
                    .assId(schemeListFo.getAssId())
                    .assType(schemeListFo.getAssType())
                    .unitId(Integer.parseInt(id))
                    .build());
        }
        assUsers.add(AssUser.builder()
                .assId(schemeListFo.getAssId())
                .assType(schemeListFo.getAssType())
                .unitId(supUnitId)
                .build());
        assUserService.saveBatch(assUsers);
        return Result.ok();
    }

    @Override
    public Result getTarget(Scheme scheme) {

        //获取出方案的id
        scheme = schemeDao.selectOne(
                new QueryWrapper<Scheme>()
                        .lambda()
                        .eq(Scheme::getAssId, scheme.getAssId())
        );

        //获取期数中的截至时间 年度等信息
        ProUnitOvertime proUnitOvertime = proUnitOvertimeDao.getOne(scheme.getId());

        //根据方案的id和本人的单位id获取出方案单位关联
        List<SchemeAgency> schemeAgencyList = schemeAgencyService.list(
                new QueryWrapper<SchemeAgency>()
                        .lambda()
                        .eq(SchemeAgency::getSchemeId, scheme.getId())
                        .eq(SchemeAgency::getAgencyId, LoginUserUtils.getLoginUserInfo().getSupUnitId())
        );
        if (CollectionUtils.isEmpty(schemeAgencyList)) {
            throw new RRException("未找到相关需填报指标，请联系系统管理员了解详情");
        }

        //获取出需要填报的指标id
        List<SchemeAgencyTarget> schemeAgencyTargetList = new ArrayList<>();
        for (SchemeAgency schemeAgency : schemeAgencyList) {
            List<SchemeAgencyTarget> schemeAgencyTargets = schemeAgencyTargetService.list(
                    new QueryWrapper<SchemeAgencyTarget>()
                            .lambda()
                            .eq(SchemeAgencyTarget::getSchemeAgencyId, schemeAgency.getId())
            );
            if (!schemeAgencyTargets.isEmpty()) {
                schemeAgencyTargetList.addAll(schemeAgencyTargets);
            }
        }
        if (CollectionUtils.isEmpty(schemeAgencyTargetList)) {
            throw new RRException("未找到相关需填报指标，请联系系统管理员了解详情");
        }

        List<SchemeAgencyTargetVo> schemeAgencyTargetVoList = ClassUtil.toClassList(schemeAgencyTargetList, SchemeAgencyTargetVo.class);

        List<DetailTargetVo> detailTargetVoList = detailTargetService.getTargetTB(schemeAgencyTargetVoList);

        SchemeVo schemeVo = ClassUtil.toClass(scheme, SchemeVo.class);
        if(proUnitOvertime != null){
            schemeVo.setProUnitOvertime(proUnitOvertime);
        }
        schemeVo.setDetailTargets(detailTargetVoList);
        return Result.ok(schemeVo);
    }

    @Override
    @Transactional
    public Result zbtb(SchemeVo sc) {

        /**
         * 添加填报记录表
         * 保存填报答案
         * 判断是否是最后一个填报
         * 否：更新自己的scheme_agency状态。
         * 是：计算结果，保存到结果历史表，更新结果到scheme_target
            * 判断是否最后一期
            * 否：将所有方案相关scheme_agency变成未填报
            * 是：更新scheme_agency为已填报，更新主表状态
         */
        //查询scheme_agency,自己的
        SchemeAgency schemeAgency = schemeAgencyService.getOne(
                new QueryWrapper<SchemeAgency>()
                        .lambda()
                        .eq(SchemeAgency::getSchemeId,sc.getId())
                        .eq(SchemeAgency::getAgencyId,LoginUserUtils.getSupUnitId())
        );
        //获取填报的期数
        List<ProUnitOvertime> proUnitOvertimeList = proUnitOvertimeService.list(
                new QueryWrapper<ProUnitOvertime>()
                        .lambda()
                        .eq(ProUnitOvertime::getSchemeId,sc.getId())
                        .eq(ProUnitOvertime::getMark,MarkEnum.NO)
                        .orderByAsc(ProUnitOvertime::getOverTime)
        );

        //添加填报记录表
        AnswerRecord answerRecord = AnswerRecord.builder()
                .saId(schemeAgency.getId())
                .createTime(new Date())
                .proUnitOvertimeId(proUnitOvertimeList.get(0).getId())
                .createUser(LoginUserUtils.getLoginUserId())
                .build();
        answerRecordService.save(answerRecord);

        //保存填报答案
        List<DetailTargetVo> detailTargetVoList = sc.getDetailTargets();
        satAnswerService.saveAnswer(detailTargetVoList,answerRecord,schemeAgency,proUnitOvertimeList.get(0));

        //更新填报状态
        schemeAgencyService.updateMark(sc,schemeAgency,proUnitOvertimeList);

        return Result.ok();
    }

    /**
     * 返回等级  0  不显示  1  黄色  2  红色
     *
     * @param sa
     * @param s
     * @param e
     * @return
     */
    public String getGradeOfPeriod(SchemeAgency sa, Date s, Date e) {
        Date startTime = sa.getStartTime();
        Date overTime = sa.getOverTime();
        //如果当前时间大于设定的结束时间，就不判断了
        if (null != overTime && new Date().getTime() > overTime.getTime()) {
            return "0";
        }
        QueryWrapper<AnswerRecord> qw = new QueryWrapper<AnswerRecord>().between("create_time", DateUtil.format(s, "yyyy-MM-dd HH:mm:ss"), DateUtil.format(e, "yyyy-MM-dd HH:mm:ss")).eq("sa_id", sa.getId());
        //判断s和e时间段有没有填报
        List<AnswerRecord> ar = answerRecordService.list(qw);
        //如果时间段中有，就返回0
        if (!CollectionUtils.isEmpty(ar)) {
            return "0";
        }

        //如果没有,判断当前时间到规定时间还有多少天
        try {
            //获取这个周期总共有多少天
            long j = DateUtil.between(s, e, DateUnit.DAY);
            //获取当前时间到这个周期的结束之间有多少天
            long k = DateUtil.between(new Date(), e, DateUnit.DAY);
            //把周期总时间除2
            double v = (double) j / 2;
            //如果除2数大于现在到周期结束的天数，就得继续判断
            if (v > (double) k) {
                double o = (double) j / 3;
                if (o > (double) k) {
                    return "2";
                }
                return "1";
            } else {
                return "0";
            }
        } catch (Exception x) {
            log.error("预警错误");
            return "0";
        }
    }



    @Override
    public Result getSchemeTargetDetailNew(SchemeFo schemeFo) {
        //获取方案
        Scheme scheme = schemeDao.selectOne(
                new QueryWrapper<Scheme>()
                        .lambda()
                        .eq(Scheme::getAssId, schemeFo.getAssId()));
        if (null == scheme) {
            return Result.ok(null);
        }
        SchemeVo schemeVo = ClassUtil.toClass(scheme, SchemeVo.class);
        schemeVo.setTarget(null);

        //查找方案关联指标
        List<SchemeTarget> schemeTargetList = schemeTargetService.list(
                new QueryWrapper<SchemeTarget>()
                        .lambda()
                        .eq(SchemeTarget::getSchemeId, schemeVo.getId())
        );

        List<SchemeTargetVo> schemeTargetVos = ClassUtil.toClassList(schemeTargetList, SchemeTargetVo.class);

        //比较结果
        compareExpect(schemeTargetVos);

        //获取指标历史业绩值
        schemeTargetHistoryAnswerService.getHistoryAnswerList(schemeTargetVos);

        //获取具体指标明细内容答案
        schemeTargetService.getAnswer(schemeTargetVos,schemeVo);

        schemeVo.setSchemeTargetVoList(schemeTargetVos);
        return Result.ok(schemeVo);
    }

    /**
     * 0未逾期 1 一个月以内 黄色 2 逾期 红色
     * @param assId
     * @return
     */
    @Override
    public String isOverdue(String assId) {
        Scheme scheme = getOne(new QueryWrapper<Scheme>().lambda()
                .eq(Scheme::getAssId, assId).select(Scheme::getId));
        if(scheme == null) return "0";
        //获取未填报的期数 并且按照期数升序排序
        List<ProUnitOvertime> proUnitOvertimes = proUnitOvertimeService.list(new QueryWrapper<ProUnitOvertime>().lambda()
                .eq(ProUnitOvertime::getSchemeId, scheme.getId())
                 .ne(ProUnitOvertime::getMark , "1")
                .orderByAsc(ProUnitOvertime::getPeriod));
        if(proUnitOvertimes.isEmpty()) return "0";
        ProUnitOvertime proUnitOvertime = proUnitOvertimes.get(0);
        //获取过期时间
        Date overTime = proUnitOvertime.getOverTime();
        //获取当前时间
        Date date = new Date();
        int compare = Long.compare(date.getTime(), overTime.getTime());
        //compare 等于1直接逾期
        if(compare == 1){
            return "2";
        }
        //糊涂工具类 时间差
        long s = DateUtil.betweenDay(date, overTime , true);
        if(s < 30){
            return "1";
        }
        return "0";
    }

    /**
     * 对业绩值与预期进行比较
     */
    private void compareExpect(List<SchemeTargetVo> schemeTargetVoList){
        for(SchemeTargetVo schemeTargetVo:schemeTargetVoList){
            if(StringUtils.isNotEmpty(schemeTargetVo.getExpect()) && StringUtils.isNotEmpty(schemeTargetVo.getAnswer())){
                //TODO
                //当前只比较数字型，且默认比大
                if(NumberUtil.isNumber(schemeTargetVo.getExpect()) && NumberUtil.isNumber(schemeTargetVo.getAnswer())){
                    if(NumberUtil.compare(Double.parseDouble(schemeTargetVo.getExpect()),Double.parseDouble(schemeTargetVo.getAnswer())) <= 0){
                        schemeTargetVo.setCompareEnum(CompareEnum.MORE_THAN);
                    }else{
                        schemeTargetVo.setCompareEnum(CompareEnum.LESS_THAN);
                    }
                }
            }
        }
    }
}
