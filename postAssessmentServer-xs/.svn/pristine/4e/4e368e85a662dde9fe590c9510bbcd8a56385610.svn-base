package com.beneway.common.service.zg;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.common.enums.MarkEnum;
import com.beneway.common.common.enums.StageEnum;
import com.beneway.common.common.enums.StatusEnum;
import com.beneway.common.common.result.Result;
import com.beneway.common.common.utils.jwt.LoginUserUtils;
import com.beneway.common.dao.zg.ZgDao;
import com.beneway.common.entity.assessstart.AssessStart;
import com.beneway.common.entity.assuser.AssUser;
import com.beneway.common.entity.majorproject.MajorProject;
import com.beneway.common.entity.zg.Zg;
import com.beneway.common.entity.zgfeedback.ZgFeedback;
import com.beneway.common.service.assessstart.AssessStartService;
import com.beneway.common.service.assuser.AssUserService;
import com.beneway.common.service.majorproject.MajorProjectService;
import com.beneway.common.service.zgfeedback.ZgFeedbackService;
import com.beneway.common.system.entity.sys_unit.SysUnit;
import com.beneway.common.system.service.sys_unit.SysUnitService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZgServiceImpl extends ServiceImpl<ZgDao , Zg> implements ZgService{

    @Autowired
    private ZgDao zgDao;

    @Autowired
    private AssessStartService assessStartService;

    @Autowired
    private ZgFeedbackService zgFeedbackService;

    @Autowired
    private SysUnitService sysUnitService;

    @Autowired
    private MajorProjectService majorProjectService;

    @Autowired
    private AssUserService assUserService;

    @Override
    @Transactional
    public Result add(Zg zg) {
        //判断是否是本项目的牵头单位，不是就不能让他发
        AssessStart assessStart = assessStartService.getOne(new QueryWrapper<AssessStart>().eq("ass_id", zg.getAssId()).select("qt_unit"));
        if(null == assessStart){
            return Result.error();
        }
        if(!assessStart.getQtUnit().equals(LoginUserUtils.getLoginUserInfo().getSupUnitId())){
           return Result.error("不是牵头单位不允许发送整改通知");
        }
        zg.setCreateTime(new Date()).setCreateUser(LoginUserUtils.getLoginUserId()).setMark(MarkEnum.NO.getMark());

        //修改主表状态
        MajorProject m = MajorProject.builder().projectStage(StageEnum.Z_WAIT_RECTIFICATION.getStage()).id(zg.getAssId()).build();
        majorProjectService.updateById(m);
        save(zg);
        //添加到相关人员
        AssUser assUser = AssUser.builder().assId(zg.getAssId()).assType(zg.getAssType()).unitId(Integer.parseInt(zg.getAgencyId())).build();
        assUserService.save(assUser);
        return Result.ok();
    }

    @Override
    public Result zgContentByProject(Zg zg) {
        List<Zg> zgList = list(new QueryWrapper<Zg>().eq("ass_id", zg.getAssId()));
        if(zgList.isEmpty()){
            return Result.ok(zgList);
        }
        for (Zg z : zgList) {
            List<ZgFeedback> feedbacks = zgFeedbackService.list(new QueryWrapper<ZgFeedback>().in("zg_id", z.getId()));
            if(!feedbacks.isEmpty()){
                z.setZgFeedbackList(feedbacks);
            }
            SysUnit s = sysUnitService.getOne(new QueryWrapper<SysUnit>().lambda().eq(SysUnit::getId, z.getAgencyId()).select(SysUnit::getUnitName));
            if (s == null){
                continue;
            }
            z.setAgencyName(s.getUnitName());
        }
        return Result.ok(zgList);
    }

    @Override
    public Result myZgMessage(Zg zg) {
        zg = lambdaQuery().eq(Zg::getAssId, zg.getAssId())
                .eq(Zg::getAssType, zg.getAssType())
                .eq(Zg::getMark , MarkEnum.NO)
                .eq(Zg::getAgencyId, LoginUserUtils.getSupUnitId())
                .one();
        return Result.ok(zg);
    }
}
