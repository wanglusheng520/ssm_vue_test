package com.beneway.common.service.schemetargethistoryanswer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.dao.schemetargethistoryanswer.SchemeTargetHistoryAnswerDao;
import com.beneway.common.entity.schemetarget.vo.SchemeTargetVo;
import com.beneway.common.entity.schemetargethistoryanswer.SchemeTargetHistoryAnswer;
import com.beneway.common.service.schemetarget.SchemeTargetService;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchemeTargetHistoryAnswerServiceImpl extends ServiceImpl<SchemeTargetHistoryAnswerDao , SchemeTargetHistoryAnswer> implements SchemeTargetHistoryAnswerService {

    private SchemeTargetHistoryAnswerService getCurrThis(){
        SchemeTargetHistoryAnswerService currentProxy = (SchemeTargetHistoryAnswerService) AopContext.currentProxy();
        return currentProxy;
    }
    @Override
    public void getHistoryAnswerList(List<SchemeTargetVo> schemeTargetVoList) {
        for(SchemeTargetVo schemeTargetVo:schemeTargetVoList){
            //获取历史答案
            schemeTargetVo.setSchemeTargetHistoryAnswerList(
                    getCurrThis().list(
                            new QueryWrapper<SchemeTargetHistoryAnswer>()
                                    .lambda()
                                    .eq(SchemeTargetHistoryAnswer::getSchemeTargetId,schemeTargetVo.getId())
                                    .orderByAsc(SchemeTargetHistoryAnswer::getCreateTime)
                    )
            );
        }
    }
}
