package com.beneway.tasks.common.job;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.beneway.common.common.enums.MarkEnum;
import com.beneway.common.dao.scheme.SchemeDao;
import com.beneway.common.dao.schemeagency.SchemeAgencyDao;
import com.beneway.common.earlywarn.entity.EarlyWarn;
import com.beneway.common.earlywarn.entity.enums.WarnTypeEnum;
import com.beneway.common.earlywarn.service.EarlyWarnService;
import com.beneway.common.entity.prounitovertime.ProUnitOvertime;
import com.beneway.common.entity.scheme.Scheme;
import com.beneway.common.entity.schemeagency.SchemeAgency;
import com.beneway.common.entity.schemeagencytarget.SchemeAgencyTarget;
import com.beneway.common.service.answerrecord.AnswerRecordService;
import com.beneway.common.service.prounitovertime.ProUnitOvertimeService;
import com.beneway.common.service.scheme.SchemeService;
import com.beneway.common.service.scheme.SchemeServiceImpl;
import com.beneway.common.service.schemeagency.SchemeAgencyService;
import com.beneway.rabbitMQ.common.rabbit.RabbitMQConstant;
import com.beneway.tasks.common.config.ScheduledOfTask;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class PeriodWarnJob implements ScheduledOfTask{

    @Autowired
    private ProUnitOvertimeService proUnitOvertimeService;
    @Autowired
    private SchemeService schemeService;
    @Autowired
    private EarlyWarnService earlyWarnService;
    @Override
    public void execute() {

        List<ProUnitOvertime> proUnitOvertimes = proUnitOvertimeService.list(
                new QueryWrapper<ProUnitOvertime>()
                        .lambda()
                        .eq(ProUnitOvertime::getMark, MarkEnum.NO));

        if(proUnitOvertimes.isEmpty()) return;

        Map<String , List<ProUnitOvertime>> map = new HashMap<>();

        for (ProUnitOvertime proUnitOvertime : proUnitOvertimes) {
            if(!map.containsKey(proUnitOvertime.getSchemeId())){
                List<ProUnitOvertime> p = new ArrayList<>();
                p.add(proUnitOvertime);
                map.put(proUnitOvertime.getSchemeId() , p);
            }else{
                map.get(proUnitOvertime.getSchemeId()).add(proUnitOvertime);
            }
        }

        for (Map.Entry<String, List<ProUnitOvertime>> res : map.entrySet()) {
            //是为了获取ass_id 和 ass_type
            Scheme scheme = schemeService.getById(res.getKey());
            List<ProUnitOvertime> value = res.getValue();
            value = value.stream().sorted(new Comparator<ProUnitOvertime>() {
                @Override
                public int compare(ProUnitOvertime o1, ProUnitOvertime o2) {
                    return Long.compare( o1.getOverTime().getTime() , o2.getOverTime().getTime() );
                }
            }).collect(Collectors.toList());

            ProUnitOvertime proUnitOvertime = value.get(0);
            //获取过期时间
            Date overTime = proUnitOvertime.getOverTime();
            //获取当前时间
            Date date = new Date();
            int compare = Long.compare(date.getTime(), overTime.getTime());

            //将以前给重置
            EarlyWarn e = EarlyWarn.builder()
                    .assId(scheme.getAssId())
                    .assType(scheme.getAssType())
                    .warnType(WarnTypeEnum.OVERDUE)
                    .warnMark(MarkEnum.YES.getMark())
                    .build();

            earlyWarnService.update(e , new UpdateWrapper<EarlyWarn>().lambda()
                    .eq(EarlyWarn::getAssId , scheme.getAssId())
                    .eq(EarlyWarn::getAssType , scheme.getAssType())
                    .eq(EarlyWarn::getWarnType , WarnTypeEnum.OVERDUE));

            EarlyWarn E = EarlyWarn.builder()
                    .assId(scheme.getAssId())
                    .assType(scheme.getAssType())
                    .warnType(WarnTypeEnum.NEAR_OVERDUE)
                    .warnMark(MarkEnum.YES.getMark())
                    .build();

            earlyWarnService.update(E , new UpdateWrapper<EarlyWarn>().lambda()
                    .eq(EarlyWarn::getAssId , scheme.getAssId())
                    .eq(EarlyWarn::getAssType , scheme.getAssType())
                    .eq(EarlyWarn::getWarnType , WarnTypeEnum.NEAR_OVERDUE));


            //compare 等于1直接逾期
            if(compare == 1){
                //添加到预警表
                EarlyWarn earlyWarn = EarlyWarn.builder()
                        .assId(scheme.getAssId())
                        .assType(scheme.getAssType())
                        .warnType(WarnTypeEnum.OVERDUE)
                        .createTime(new Date())
                        .build();
                earlyWarnService.save(earlyWarn);
            }
            //糊涂工具类 时间差
            long s = DateUtil.betweenDay(date, overTime , true);
            if(s < 30){
                //添加到预警表 临近逾期
                EarlyWarn earlyWarn = EarlyWarn.builder()
                        .assId(scheme.getAssId())
                        .assType(scheme.getAssType())
                        .warnType(WarnTypeEnum.NEAR_OVERDUE)
                        .createTime(new Date())
                        .build();
                earlyWarnService.save(earlyWarn);
            }
        }
    }
}
