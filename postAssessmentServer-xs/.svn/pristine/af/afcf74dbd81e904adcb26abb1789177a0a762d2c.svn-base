package com.beneway.common.service.satanswer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.common.utils.ClassUtil;
import com.beneway.common.dao.satanswer.SatAnswerDao;
import com.beneway.common.entity.answerrecord.AnswerRecord;
import com.beneway.common.entity.detailtarget.enums.TypeEnum;
import com.beneway.common.entity.detailtarget.vo.DetailTargetVo;
import com.beneway.common.entity.detailtargetitem.DetailTargetItem;
import com.beneway.common.entity.detailtargetitem.vo.DetailTargetItemVo;
import com.beneway.common.entity.prounitovertime.ProUnitOvertime;
import com.beneway.common.entity.satanswer.SatAnswer;
import com.beneway.common.entity.satanswer.vo.SatAnswerVo;
import com.beneway.common.entity.scheme.vo.SchemeVo;
import com.beneway.common.entity.schemeagency.SchemeAgency;
import com.beneway.common.entity.schemeagencytarget.SchemeAgencyTarget;
import com.beneway.common.entity.schemetarget.SchemeTarget;
import com.beneway.common.entity.schemetargethistoryanswer.SchemeTargetHistoryAnswer;
import com.beneway.common.service.schemeagency.SchemeAgencyService;
import com.beneway.common.service.schemeagencytarget.SchemeAgencyTargetService;
import com.beneway.common.service.schemetarget.SchemeTargetService;
import com.beneway.common.service.schemetargethistoryanswer.SchemeTargetHistoryAnswerService;
import com.beneway.common.system.entity.sys_unit.SysUnit;
import com.beneway.common.system.entity.sys_unit.vo.SysUnitComVo;
import com.beneway.common.system.service.sys_unit.SysUnitService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SatAnswerServiceImpl extends ServiceImpl<SatAnswerDao , SatAnswer> implements SatAnswerService {

    @Autowired
    SchemeAgencyService schemeAgencyService;
    @Autowired
    SchemeAgencyTargetService schemeAgencyTargetService;
    @Autowired
    SchemeTargetService schemeTargetService;
    @Autowired
    SchemeTargetHistoryAnswerService schemeTargetHistoryAnswerService;
    @Autowired
    SysUnitService sysUnitService;

    private SatAnswerService getCurrThis(){
        SatAnswerService currentProxy = (SatAnswerService) AopContext.currentProxy();
        return currentProxy;
    }
    @Override
    public void saveAnswer(List<DetailTargetVo> detailTargetVoList, AnswerRecord answerRecord, SchemeAgency schemeAgency, ProUnitOvertime proUnitOvertime) {
        SchemeAgencyTarget schemeAgencyTarget = null;
        SatAnswer satAnswer = null;
        List<SatAnswer> satAnswerList = new ArrayList<>();
        for (DetailTargetVo detailTargetVo : detailTargetVoList) {
            if(detailTargetVo.getType().equals(TypeEnum.FORMULA.getType())){
                for (DetailTargetItemVo detailTargetItem : detailTargetVo.getDetailTargetItemVoList()) {
                    schemeAgencyTarget = schemeAgencyTargetService.getOne(
                            new QueryWrapper<SchemeAgencyTarget>()
                                    .lambda()
                                    .eq(SchemeAgencyTarget::getSchemeAgencyId, schemeAgency.getId())
                                    .eq(SchemeAgencyTarget::getTargetId, detailTargetVo.getId())
                                    .eq(SchemeAgencyTarget::getItemId, detailTargetItem.getId())
                    );
                    satAnswer = SatAnswer.builder()
                            .answerRecordId(answerRecord.getId())
                            .content(detailTargetItem.getTargetValue())
                            .satId(schemeAgencyTarget.getId())
                            .supportingMaterials(detailTargetVo.getSupportingMaterials())
                            .createTime(new Date())
                            .build();
                    satAnswerList.add(satAnswer);
                }
            }else{
                schemeAgencyTarget = schemeAgencyTargetService.getOne(
                        new QueryWrapper<SchemeAgencyTarget>()
                                .lambda()
                                .eq(SchemeAgencyTarget::getSchemeAgencyId, schemeAgency.getId())
                                .eq(SchemeAgencyTarget::getTargetId, detailTargetVo.getId())
                );
                satAnswer = SatAnswer.builder()
                        .answerRecordId(answerRecord.getId())
                        .content(detailTargetVo.getContent())
                        .satId(schemeAgencyTarget.getId())
                        .supportingMaterials(detailTargetVo.getSupportingMaterials())
                        .createTime(new Date())
                        .build();
                satAnswerList.add(satAnswer);

                //保存答案到scheme_target
                SchemeTarget schemeTarget = schemeTargetService.getOne(new QueryWrapper<SchemeTarget>()
                        .lambda()
                        .eq(SchemeTarget::getSchemeId, schemeAgency.getSchemeId())
                        .eq(SchemeTarget::getDetailTargetId, detailTargetVo.getId()));
                double expect = 0;
                double answer = 0;
                try {
                    expect = Double.parseDouble(schemeTarget.getExpect());
                    answer = Double.parseDouble(detailTargetVo.getContent());
                    if(expect > answer){
                        schemeTarget.setResult("1");
                    }
                }catch (Exception e){
                    log.error("------添加答案 对比 转换错误------");
                }
                schemeTarget.setAnswer(detailTargetVo.getContent());
                schemeTargetService.updateById(schemeTarget);
                //将答案保存到scheme_target_history
                schemeTargetHistoryAnswerService.save(
                        SchemeTargetHistoryAnswer.builder()
                                .historyAnswer(detailTargetVo.getContent())
                                .schemeTargetId(schemeTarget.getId())
                                .createTime(new Date())
                                .period(proUnitOvertime.getPeriod())
                                .build()
                );
            }

        }
        getCurrThis().saveBatch(satAnswerList);
    }

    @Override
    public void getSatAnswer(DetailTargetVo detailTargetVo, SchemeVo schemeVo) {
        //查找scheme_agency表
        List<SchemeAgency> schemeAgencyList = schemeAgencyService.list(
                new QueryWrapper<SchemeAgency>()
                        .lambda()
                        .eq(SchemeAgency::getSchemeId, schemeVo.getId())
        );
        List<String> saCollect = schemeAgencyList.stream().map(res -> {
            return String.valueOf(res.getId());
        }).collect(Collectors.toList());

        SchemeAgencyTarget schemeAgencyTarget = null;
        if(detailTargetVo.getType().equals(TypeEnum.FORMULA.getType())){
            //公式型
            List<DetailTargetItemVo> detailTargetItemVoList = detailTargetVo.getDetailTargetItemVoList();
            for (DetailTargetItemVo detailTargetItemVo : detailTargetItemVoList) {
                //查看是否有答案，查找sat
                schemeAgencyTarget = schemeAgencyTargetService.getOne(
                        new QueryWrapper<SchemeAgencyTarget>()
                                .lambda()
                                .eq(SchemeAgencyTarget::getTargetId, detailTargetVo.getId())
                                .eq(SchemeAgencyTarget::getItemId, detailTargetItemVo.getId())
                                .in(SchemeAgencyTarget::getSchemeAgencyId, saCollect)
                );
                if (null != schemeAgencyTarget) {
                    detailTargetItemVo.setSysUnitComVo(getSysUnitComVo(schemeAgencyTarget.getSchemeAgencyId()));

                    List<SatAnswer> satAnswerList = getCurrThis().list(
                            new QueryWrapper<SatAnswer>()
                                    .lambda()
                                    .eq(SatAnswer::getSatId,schemeAgencyTarget.getId())
                                    .orderByDesc(SatAnswer::getCreateTime)
                    );
                    detailTargetItemVo.setSatAnswerVoList(ClassUtil.toClassList(satAnswerList,SatAnswerVo.class));
                }
            }
            detailTargetVo.setDetailTargetItemVoList(detailTargetItemVoList);
        }else{
            //获取sat表
            //查看是否有答案，查找sat
            schemeAgencyTarget = schemeAgencyTargetService.getOne(
                    new QueryWrapper<SchemeAgencyTarget>()
                            .lambda()
                            .eq(SchemeAgencyTarget::getTargetId, detailTargetVo.getId())
                            .in(SchemeAgencyTarget::getSchemeAgencyId, saCollect)
            );
            if (null != schemeAgencyTarget) {
                detailTargetVo.setSysUnitComVo(getSysUnitComVo(schemeAgencyTarget.getSchemeAgencyId()));

                List<SatAnswer> satAnswerList = getCurrThis().list(
                        new QueryWrapper<SatAnswer>()
                                .lambda()
                                .eq(SatAnswer::getSatId,schemeAgencyTarget.getId())
                                .orderByDesc(SatAnswer::getCreateTime)
                );
                detailTargetVo.setSatAnswerVoList(ClassUtil.toClassList(satAnswerList, SatAnswerVo.class));
            }
        }
    }

    public SysUnitComVo getSysUnitComVo(String saId){
        SchemeAgency schemeAgency = schemeAgencyService.getOne(
                new QueryWrapper<SchemeAgency>()
                        .lambda()
                        .eq(SchemeAgency::getId, saId)
        );
        SysUnitComVo sysUnitComVo = sysUnitService.getUnitInfo(schemeAgency.getAgencyId());
        return sysUnitComVo;
    }
}
