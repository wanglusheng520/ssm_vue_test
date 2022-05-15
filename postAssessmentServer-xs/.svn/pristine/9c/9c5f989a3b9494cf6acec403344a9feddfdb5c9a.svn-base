package com.beneway.common.service.schemetarget;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.common.enums.MarkEnum;
import com.beneway.common.common.enums.StatusEnum;
import com.beneway.common.common.result.Result;
import com.beneway.common.common.utils.ClassUtil;
import com.beneway.common.dao.schemetarget.SchemeTargetDao;
import com.beneway.common.entity.detailtarget.DetailTarget;
import com.beneway.common.entity.detailtarget.enums.TypeEnum;
import com.beneway.common.entity.detailtarget.fo.DetailTargetFo;
import com.beneway.common.entity.detailtarget.vo.DetailTargetVo;
import com.beneway.common.entity.detailtargetitem.DetailTargetItem;
import com.beneway.common.entity.scheme.Scheme;
import com.beneway.common.entity.scheme.fo.SchemeFo;
import com.beneway.common.entity.scheme.vo.SchemeVo;
import com.beneway.common.entity.schemeagency.SchemeAgency;
import com.beneway.common.entity.schemeagencytarget.SchemeAgencyTarget;
import com.beneway.common.entity.schemetarget.SchemeTarget;
import com.beneway.common.entity.schemetarget.fo.SchemeTargetFo;
import com.beneway.common.entity.schemetarget.vo.SchemeTargetVo;
import com.beneway.common.entity.schemetargethistoryanswer.SchemeTargetHistoryAnswer;
import com.beneway.common.service.detailtarget.DetailTargetService;
import com.beneway.common.service.detailtargetitem.DetailTargetItemService;
import com.beneway.common.service.satanswer.SatAnswerService;
import com.beneway.common.service.scheme.SchemeService;
import com.beneway.common.service.schemeagency.SchemeAgencyService;
import com.beneway.common.service.schemeagencytarget.SchemeAgencyTargetService;
import com.beneway.common.service.schemetargethistoryanswer.SchemeTargetHistoryAnswerService;
import com.beneway.common.system.service.sys_unit.SysUnitService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchemeTargetServiceImp extends ServiceImpl<SchemeTargetDao, SchemeTarget> implements SchemeTargetService{

    @Autowired
    SchemeService schemeService;
    @Autowired
    DetailTargetService detailTargetService;
    @Autowired
    SchemeTargetHistoryAnswerService schemeTargetHistoryAnswerService;
    @Autowired
    SchemeAgencyService schemeAgencyService;
    @Autowired
    SchemeAgencyTargetService schemeAgencyTargetService;
    @Autowired
    SatAnswerService satAnswerService;
    @Autowired
    SchemeTargetService schemeTargetService;


    private SchemeTargetService getCurrThis(){
        SchemeTargetService currentProxy = (SchemeTargetService) AopContext.currentProxy();
        return currentProxy;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveList(List<DetailTargetFo> detailTargetFoList, Scheme scheme) {
        SchemeTarget schemeTarget = null;
        List<SchemeTarget> schemeTargetList = new ArrayList<>();
        for(DetailTargetFo detailTargetFo:detailTargetFoList){
            schemeTarget = SchemeTarget.builder()
                    .schemeId(scheme.getId())
                    .detailTargetId(detailTargetFo.getId())
                    .expect(detailTargetFo.getExpect())
                    .build();
            schemeTargetList.add(schemeTarget);
        }
        getCurrThis().saveBatch(schemeTargetList);
    }

    @Override
    public void getAnswer(List<SchemeTargetVo> schemeTargetVoList,SchemeVo schemeVo) {
        for(SchemeTargetVo schemeTargetVo:schemeTargetVoList){
            DetailTarget detailTarget = detailTargetService.getOne(
                    new QueryWrapper<DetailTarget>()
                            .lambda()
                            .eq(DetailTarget::getId, schemeTargetVo.getDetailTargetId())
            );
            DetailTargetVo detailTargetVo = ClassUtil.toClass(detailTarget, DetailTargetVo.class);

            //获取指标下级关联数据
            detailTargetService.getDetailTargetAss(detailTargetVo);
            //查找答案
            satAnswerService.getSatAnswer(detailTargetVo,schemeVo);

            schemeTargetVo.setDetailTargetVo(detailTargetVo);

        }
    }
}
