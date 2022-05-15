package com.beneway.common.service.zgfeedback;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.common.enums.MarkEnum;
import com.beneway.common.common.enums.StageEnum;
import com.beneway.common.common.enums.StatusEnum;
import com.beneway.common.common.result.Result;
import com.beneway.common.common.utils.jwt.LoginUserUtils;
import com.beneway.common.dao.zgfeedback.ZgFeedbackDao;
import com.beneway.common.entity.majorproject.MajorProject;
import com.beneway.common.entity.zg.Zg;
import com.beneway.common.entity.zgfeedback.ZgFeedback;
import com.beneway.common.service.majorproject.MajorProjectService;
import com.beneway.common.service.zg.ZgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class ZgFeedbackServiceImpl extends ServiceImpl<ZgFeedbackDao , ZgFeedback> implements ZgFeedbackService {

    @Autowired
    private ZgService zgService;
    @Autowired
    private MajorProjectService majorProjectService;

    @Override
    @Transactional
    public Result feedback(ZgFeedback zgFeedback) {
        //验证身份，是否能反馈
        Integer supUnitId = LoginUserUtils.getLoginUserInfo().getSupUnitId();
        Zg z = zgService.getOne(new QueryWrapper<Zg>().eq("id", zgFeedback.getZgId()));
        if(!z.getAgencyId().equals(String.valueOf(supUnitId))){
            return Result.error("您不能进行反馈");
        }
        zgFeedback.setCreateUser(LoginUserUtils.getLoginUserId()).setCreateTime(new Date());
        save(zgFeedback);
        //修改整改表状态
        z.setMark(MarkEnum.YES.getMark());
        zgService.updateById(z);

        //修改主表状态
        MajorProject m = MajorProject.builder().projectStage(StageEnum.F_ALREADY_RECTIFICATION.getStage()).id(z.getAssId()).build();
        majorProjectService.updateById(m);
        return Result.ok();
    }
}
