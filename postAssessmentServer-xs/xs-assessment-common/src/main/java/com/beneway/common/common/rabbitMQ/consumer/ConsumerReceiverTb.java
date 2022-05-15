package com.beneway.common.common.rabbitMQ.consumer;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.beneway.common.common.rabbitMQ.provider.ProviderUtils;
import com.beneway.common.common.rabbitMQ.provider.RabbitMQConstant;
import com.beneway.common.common.utils.ClassUtil;
import com.beneway.common.common.utils.exception.RRException;
import com.beneway.common.entity.answerrecord.AnswerRecord;
import com.beneway.common.entity.detailtarget.DetailTarget;
import com.beneway.common.entity.detailtarget.enums.TypeEnum;
import com.beneway.common.entity.detailtargetitemoperator.DetailTargetItemOperator;
import com.beneway.common.entity.prounitovertime.ProUnitOvertime;
import com.beneway.common.entity.satanswer.SatAnswer;
import com.beneway.common.entity.scheme.Scheme;
import com.beneway.common.entity.schemeagency.SchemeAgency;
import com.beneway.common.entity.schemeagencytarget.SchemeAgencyTarget;
import com.beneway.common.entity.schemeagencytarget.vo.SchemeAgencyTargetVo;
import com.beneway.common.entity.schemetarget.SchemeTarget;
import com.beneway.common.entity.schemetargethistoryanswer.SchemeTargetHistoryAnswer;
import com.beneway.common.service.answerrecord.AnswerRecordService;
import com.beneway.common.service.detailtarget.DetailTargetService;
import com.beneway.common.service.detailtargetitemoperator.DetailTargetItemOperatorService;
import com.beneway.common.service.prounitovertime.ProUnitOvertimeService;
import com.beneway.common.service.satanswer.SatAnswerService;
import com.beneway.common.service.schemeagency.SchemeAgencyService;
import com.beneway.common.service.schemeagencytarget.SchemeAgencyTargetService;
import com.beneway.common.service.schemetarget.SchemeTargetService;
import com.beneway.common.service.schemetargethistoryanswer.SchemeTargetHistoryAnswerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ConsumerReceiverTb {

    @Autowired
    SchemeAgencyService schemeAgencyService;
    @Autowired
    SchemeTargetService schemeTargetService;
    @Autowired
    DetailTargetService detailTargetService;
    @Autowired
    SchemeAgencyTargetService schemeAgencyTargetService;
    @Autowired
    AnswerRecordService answerRecordService;
    @Autowired
    SatAnswerService satAnswerService;
    @Autowired
    DetailTargetItemOperatorService detailTargetItemOperatorService;
    @Autowired
    SchemeTargetHistoryAnswerService schemeTargetHistoryAnswerService;
    @Autowired
    ProUnitOvertimeService proUnitOvertimeService;


    //@RabbitListener(queues = RabbitMQConstant.QUEUE_TOPIC_TB)//监听的队列名称
    @RabbitHandler
    public void processTb(Map<String,Object> message){
        /**
         * 找到scheme_target指标，并找出公式的指标
         * 找到answer_record计算的期数
         * 根据ar的id，找到sat_answer全部答案
         */
        String schemeId = message.get("schemeId").toString();
        String proUnitOvertimeId = message.get("proUnitOvertimeId").toString();

        ProUnitOvertime proUnitOvertime = proUnitOvertimeService.getById(proUnitOvertimeId);

        List<SchemeTarget> schemeTargetList = schemeTargetService.list(
                new QueryWrapper<SchemeTarget>()
                        .lambda()
                        .eq(SchemeTarget::getSchemeId,schemeId)
        );
        List<String> detailTargetIds = schemeTargetList.stream().map(SchemeTarget::getDetailTargetId).collect(Collectors.toList());

        //找到需要计算的指标
        List<DetailTarget> detailTargetList = detailTargetService.list(
                new QueryWrapper<DetailTarget>()
                        .lambda()
                        .eq(DetailTarget::getType,TypeEnum.FORMULA.getType())
                        .in(DetailTarget::getId,detailTargetIds)
        );
        //获取指标id
        detailTargetIds = detailTargetList.stream().map(DetailTarget::getId).distinct().collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(detailTargetIds)) {
            //获取方案单位关联
            List<SchemeAgency> schemeAgencyList = schemeAgencyService.list(
                    new QueryWrapper<SchemeAgency>()
                            .lambda()
                            .eq(SchemeAgency::getSchemeId, schemeId)
                            .select(SchemeAgency::getId)
            );
            //获取方案单位关联id
            List<String> schemeAgencyIds = schemeAgencyList.stream().map(SchemeAgency::getId).collect(Collectors.toList());

            //获取  方案单位指标关联
            List<SchemeAgencyTarget> satList = schemeAgencyTargetService.list(
                    new QueryWrapper<SchemeAgencyTarget>()
                            .lambda()
                            .in(SchemeAgencyTarget::getTargetId, detailTargetIds)
                            .in(SchemeAgencyTarget::getSchemeAgencyId, schemeAgencyIds));
            List<String> satIds = satList.stream().map(SchemeAgencyTarget::getId).collect(Collectors.toList());
            List<SchemeAgencyTargetVo> schemeAgencyTargetVo = ClassUtil.toClassList(satList, SchemeAgencyTargetVo.class);

            //获取是答案的期数
            List<AnswerRecord> answerRecordList = answerRecordService.list(
                    new QueryWrapper<AnswerRecord>()
                            .lambda()
                            .eq(AnswerRecord::getProUnitOvertimeId, proUnitOvertimeId)
            );
            List<String> answerRecordIds = answerRecordList.stream().map(AnswerRecord::getId).collect(Collectors.toList());

            List<SatAnswer> satAnswerList = satAnswerService.list(
                    new QueryWrapper<SatAnswer>()
                            .lambda()
                            .in(SatAnswer::getAnswerRecordId, answerRecordIds)
                            .in(SatAnswer::getSatId, satIds)
            );
            //将答案转map然后放到 方案单位指标关联 表中
            Map<String, String> satAnswersMap = satAnswerList.stream().collect(Collectors.toMap(SatAnswer::getSatId, SatAnswer::getContent));
            for (SchemeAgencyTargetVo s : schemeAgencyTargetVo) {
                String answer = satAnswersMap.get(s.getId());
                if (StringUtils.isNotBlank(answer)) {
                    s.setContent(answer);
                }
            }
            Map<String, String> itemIdAnswer = schemeAgencyTargetVo.stream().collect(Collectors.toMap(SchemeAgencyTargetVo::getItemId, SchemeAgencyTargetVo::getContent));

            for (DetailTarget detailTarget : detailTargetList) {
                //找出运算符
                List<DetailTargetItemOperator> dtios = detailTargetItemOperatorService.list(
                        new QueryWrapper<DetailTargetItemOperator>()
                                .lambda()
                                .eq(DetailTargetItemOperator::getDetailTargetId, detailTarget.getId())
                );
                //按照标识排序
                dtios = dtios.stream().sorted(new Comparator<DetailTargetItemOperator>() {
                    @Override
                    public int compare(DetailTargetItemOperator o1, DetailTargetItemOperator o2) {
                        return o1.getTag() - o2.getTag();
                    }
                }).collect(Collectors.toList());
                Double res = null;
                for (int i = 0; i < dtios.size(); i++) {
                    if (i == 0) {
                        String lastItemId = dtios.get(i).getLastItemId();
                        String nextItemId = dtios.get(i).getNextItemId();
                        Double last = Double.valueOf(itemIdAnswer.get(lastItemId));
                        Double next = Double.valueOf(itemIdAnswer.get(nextItemId));
                        res = calculate(last, next, dtios.get(i).getOperator());
                    } else {
                        String nextItemId = dtios.get(i).getNextItemId();
                        Double next = Double.valueOf(itemIdAnswer.get(nextItemId));
                        res = calculate(res, next, dtios.get(i).getOperator());
                    }
                }
                //更新scheme_target中的答案数据
                SchemeTarget st = schemeTargetService.getOne(new QueryWrapper<SchemeTarget>()
                        .lambda()
                        .eq(SchemeTarget::getSchemeId, schemeId)
                        .eq(SchemeTarget::getDetailTargetId, detailTarget.getId()));

                st.setAnswer(res.toString());
                schemeTargetService.updateById(st);
                //保存历史
                schemeTargetHistoryAnswerService.save(
                        SchemeTargetHistoryAnswer.builder()
                                .historyAnswer(res.toString())
                                .schemeTargetId(st.getId())
                                .createTime(new Date())
                                .period(proUnitOvertime.getPeriod())
                                .build()
                );
            }
        }
        log.error("------计算完成------");

        //告诉mq，预警一下
        Map<String,Object> map = new HashMap<>();
        map.put("schemeId",schemeId);
        map.put("proUnitOvertimeId",proUnitOvertimeId);
        ProviderUtils.providerAdd(RabbitMQConstant.EXCHANGE_TOPIC_TB, RabbitMQConstant.ROUTING_KEY_TOPIC_YJ, map);
    }

    public Double calculate(Double last , Double next , String operator) {
        Double res = null;
        switch (operator) {
            case "+":
                res = NumberUtil.add(last, next);
                break;
            case "-":
                res = NumberUtil.sub(last, next);
                break;
            case "*":
                res = NumberUtil.round(NumberUtil.mul(last, next), 2).doubleValue();
                break;
            case "÷":
                res = NumberUtil.round(NumberUtil.div(last, next), 2).doubleValue();
                break;
        }
        return res;
    }
}
