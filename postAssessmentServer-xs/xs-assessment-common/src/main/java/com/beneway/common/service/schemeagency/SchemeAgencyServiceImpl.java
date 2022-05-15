package com.beneway.common.service.schemeagency;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.base.BaseMainService;
import com.beneway.common.base.entity.BaseMain;
import com.beneway.common.common.enums.MarkEnum;
import com.beneway.common.common.enums.StageEnum;
import com.beneway.common.common.rabbitMQ.provider.ProviderUtils;
import com.beneway.common.common.rabbitMQ.provider.RabbitMQConstant;
import com.beneway.common.common.utils.exception.RRException;
import com.beneway.common.common.utils.jwt.LoginUserUtils;
import com.beneway.common.dao.schemeagency.SchemeAgencyDao;
import com.beneway.common.entity.detailtarget.fo.DetailTargetFo;
import com.beneway.common.entity.detailtargetitem.DetailTargetItem;
import com.beneway.common.entity.detailtargetitem.vo.DetailTargetItemVo;
import com.beneway.common.entity.operationlog.OperationLog;
import com.beneway.common.entity.prounitovertime.ProUnitOvertime;
import com.beneway.common.entity.scheme.Scheme;
import com.beneway.common.entity.scheme.vo.SchemeVo;
import com.beneway.common.entity.schemeagency.SchemeAgency;
import com.beneway.common.entity.schemeagencytarget.SchemeAgencyTarget;
import com.beneway.common.service.operationlog.OperationLogService;
import com.beneway.common.service.prounitovertime.ProUnitOvertimeService;
import com.beneway.common.service.schemeagencytarget.SchemeAgencyTargetService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SchemeAgencyServiceImpl extends ServiceImpl<SchemeAgencyDao , SchemeAgency> implements SchemeAgencyService {

    @Autowired
    SchemeAgencyTargetService schemeAgencyTargetService;
    @Autowired
    ProUnitOvertimeService proUnitOvertimeService;
    @Autowired
    BaseMainService baseMainService;
    @Autowired
    private OperationLogService operationLogService;

    private SchemeAgencyService getCurrThis(){
        SchemeAgencyService currentProxy = (SchemeAgencyService) AopContext.currentProxy();
        return currentProxy;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveList(List<DetailTargetFo> detailTargetFoList, Scheme scheme) {
        StringBuffer xtUnit = new StringBuffer();
        String schemeId = scheme.getId();
        Map<Integer, String> map = new HashMap<>();
        SchemeAgency schemeAgency = null;
        SchemeAgencyTarget schemeAgencyTarget = null;
        List<SchemeAgencyTarget> schemeAgencyTargetList = new ArrayList<>();
        for (DetailTargetFo detailTargetFo : detailTargetFoList) {
            if (null != detailTargetFo.getAgencyId()) {
                //防止单位重复添加schemeAgency
                schemeAgency = getSchemeAgency(map,detailTargetFo.getAgencyId(),schemeAgency,schemeId,xtUnit);
                //添加schemeAgencyTarget
                schemeAgencyTarget = SchemeAgencyTarget.builder()
                        .schemeAgencyId(schemeAgency.getId())
                        .targetId(detailTargetFo.getId())
                        .build();
                schemeAgencyTargetList.add(schemeAgencyTarget);
            }else{
                if (detailTargetFo.getDetailTargetItemVoList() != null) {
                    for (DetailTargetItemVo detailTargetItemVo : detailTargetFo.getDetailTargetItemVoList()) {
                        if (null != detailTargetItemVo.getAgencyId()) {
                            //防止单位重复添加schemeAgency
                            schemeAgency = getSchemeAgency(map,detailTargetItemVo.getAgencyId(),schemeAgency,schemeId,xtUnit);
                            //添加schemeAgencyTarget
                            schemeAgencyTarget = SchemeAgencyTarget.builder()
                                    .schemeAgencyId(schemeAgency.getId())
                                    .targetId(detailTargetItemVo.getDetailTargetId())
                                    .itemId(detailTargetItemVo.getId())
                                    .build();
                            schemeAgencyTargetList.add(schemeAgencyTarget);
                        }else{
                            throw new RRException("请将填报单位填写完整");
                        }
                    }
                }else{
                    throw new RRException("请将填报单位填写完整");
                }
            }
        }
        //保存schemeAgencyTarget表
        schemeAgencyTargetService.saveBatch(schemeAgencyTargetList);
        return xtUnit.toString();
    }

    public SchemeAgency getSchemeAgency(Map<Integer, String> map,Integer agencyId,SchemeAgency schemeAgency,String schemeId,StringBuffer xtUnit){
        if(!map.containsKey(agencyId)){
            //保存SchemeAgency关联表
            schemeAgency = SchemeAgency.builder()
                    .schemeId(schemeId)
                    .agencyId(agencyId)
                    .createTime(new Date())
                    .createUser(LoginUserUtils.getLoginUserId())
                    .mark(MarkEnum.NO)
                    .build();
            getCurrThis().save(schemeAgency);
            map.put(agencyId, schemeAgency.getId());
            xtUnit.append(agencyId).append(",");
            return schemeAgency;
        }else{
            schemeAgency = SchemeAgency.builder()
                    .id(map.get(agencyId))
                    .build();
            return schemeAgency;
        }
    }

    @Override
    public void updateMark(SchemeVo schemeVo,SchemeAgency schemeAgency,List<ProUnitOvertime> proUnitOvertimeList) {
        //判断是否最后一个填报
        List<SchemeAgency> schemeAgencyList = getCurrThis().list(
                new QueryWrapper<SchemeAgency>()
                        .lambda()
                        .eq(SchemeAgency::getSchemeId,schemeVo.getId())
                        .eq(SchemeAgency::getMark,MarkEnum.NO)
        );
        if(schemeAgencyList.size()>1){
            //不是最后一个填报,更新scheme_agency填报标记
            schemeAgency.setMark(MarkEnum.YES);
            getCurrThis().updateById(schemeAgency);
        }else{
            //最后一个填报
            //将当前的过期时间表的状态设置已填报
            proUnitOvertimeService.updateById(proUnitOvertimeList.get(0).setMark(MarkEnum.YES));
            //是否最后一期
            if(proUnitOvertimeList.size()>1){
                //将方案相关的所有填报标记改为未填报
                getCurrThis().update(new UpdateWrapper<SchemeAgency>()
                        .lambda()
                        .set(SchemeAgency::getMark,MarkEnum.NO)
                        .eq(SchemeAgency::getSchemeId,schemeVo.getId())
                );
            }else{
                schemeAgency.setMark(MarkEnum.YES);
                getCurrThis().updateById(schemeAgency);
                //更新主表状态
                BaseMain baseMain = new BaseMain(schemeVo.getAssType(),schemeVo.getAssId());
                baseMainService.updateMain(baseMain,StageEnum.D_EVALUATED);
                //添加到日志表
                OperationLog o = OperationLog.builder()
                        .assType(schemeVo.getAssType())
                        .assId(schemeVo.getAssId())
                        .stage(StageEnum.D_EVALUATED.getStage())
                        .createTime(new Date())
                        .build();
                operationLogService.save(o);
            }
            //告诉mq，需要进行一次计算
            Map<String,Object> map = new HashMap<>();
            map.put("schemeId",schemeVo.getId());
            map.put("proUnitOvertimeId",proUnitOvertimeList.get(0).getId());
            ProviderUtils.providerAdd(RabbitMQConstant.EXCHANGE_TOPIC_TB, RabbitMQConstant.ROUTING_KEY_TOPIC_TB, map);
        }
    }
}
