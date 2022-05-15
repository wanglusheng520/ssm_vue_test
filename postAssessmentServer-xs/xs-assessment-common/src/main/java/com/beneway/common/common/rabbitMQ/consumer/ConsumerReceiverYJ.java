package com.beneway.common.common.rabbitMQ.consumer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.beneway.common.common.enums.MarkEnum;
import com.beneway.common.common.rabbitMQ.provider.RabbitMQConstant;
import com.beneway.common.earlywarn.entity.EarlyWarn;
import com.beneway.common.earlywarn.entity.enums.WarnTypeEnum;
import com.beneway.common.earlywarn.service.EarlyWarnService;
import com.beneway.common.entity.detailtarget.DetailTarget;
import com.beneway.common.entity.detailtarget.enums.TypeEnum;
import com.beneway.common.entity.scheme.Scheme;
import com.beneway.common.entity.schemetarget.SchemeTarget;
import com.beneway.common.service.detailtarget.DetailTargetService;
import com.beneway.common.service.scheme.SchemeService;
import com.beneway.common.service.schemetarget.SchemeTargetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ConsumerReceiverYJ {

    @Autowired
    private SchemeTargetService schemeTargetService;
    
    @Autowired
    private DetailTargetService detailTargetService;

    @Autowired
    private SchemeService schemeService;

    @Autowired
    private EarlyWarnService earlyWarnService;

    //@RabbitListener(queues = RabbitMQConstant.QUEUE_TOPIC_YJ)//监听的队列名称
    @RabbitHandler
    public void processYJ(Map<String,Object> message){

        String schemeId = message.get("schemeId").toString();
        String proUnitOvertimeId = message.get("proUnitOvertimeId").toString();

        //是为了获取ass_id 和 ass_type
        Scheme scheme = schemeService.getById(schemeId);

        //获取方案指标关联
        List<SchemeTarget> schemeTargets = schemeTargetService.list(new QueryWrapper<SchemeTarget>()
                .lambda().eq(SchemeTarget::getSchemeId, schemeId));

        boolean flag = false;
        int i = 0;
        for (SchemeTarget schemeTarget : schemeTargets) {
            DetailTarget detailTarget = detailTargetService.getOne(new QueryWrapper<DetailTarget>().lambda()
                    .eq(DetailTarget::getId, schemeTarget.getDetailTargetId()).select(DetailTarget::getType));
            if(detailTarget.getType().equals(TypeEnum.NUMBER.getType()) || detailTarget.getType().equals(TypeEnum.FORMULA.getType())){
                String answer = schemeTarget.getAnswer();
                String expect = schemeTarget.getExpect();
                try {
                    double a = Double.parseDouble(answer);
                    double e = Double.parseDouble(expect);
                    if(a < e){
                        flag = true;
                        i++;
                    }
                }catch (Exception ex){
                    log.error("mq 预警 类型 转换错误");
                }
            }
        }

        //将以前给重置
        EarlyWarn e = EarlyWarn.builder()
                .assId(scheme.getAssId())
                .assType(scheme.getAssType())
                .warnType(WarnTypeEnum.NO_STANDARD)
                .warnMark(MarkEnum.YES.getMark())
                .build();

        earlyWarnService.update(e , new UpdateWrapper<EarlyWarn>().lambda()
                .eq(EarlyWarn::getAssId , scheme.getAssId())
                .eq(EarlyWarn::getAssType , scheme.getAssType())
                .eq(EarlyWarn::getWarnType , WarnTypeEnum.NO_STANDARD));

        if(flag){
            //添加到预警表
            EarlyWarn earlyWarn = EarlyWarn.builder()
                    .assId(scheme.getAssId())
                    .assType(scheme.getAssType())
                    .warnType(WarnTypeEnum.NO_STANDARD)
                    .createTime(new Date())
                    .count(i)
                    .build();
            earlyWarnService.save(earlyWarn);
        }

    }

}
