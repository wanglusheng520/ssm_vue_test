package com.beneway.common.service.majorproject;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.base.BaseService;
import com.beneway.common.common.enums.StageEnum;
import com.beneway.common.common.enums.StatusEnum;
import com.beneway.common.common.result.Result;
import com.beneway.common.common.utils.ClassUtil;
import com.beneway.common.common.utils.jwt.LoginUserUtils;
import com.beneway.common.common.utils.page.PageUtils;
import com.beneway.common.dao.majorproject.MajorProjectDao;
import com.beneway.common.earlywarn.service.EarlyWarnService;
import com.beneway.common.entity.judgmentin.JudgmentIn;
import com.beneway.common.entity.majorproject.MajorProject;
import com.beneway.common.entity.majorproject.fo.MajorProjectQueryFo;
import com.beneway.common.entity.majorproject.vo.MajorProjectVo;
import com.beneway.common.entity.putrule.PutRule;
import com.beneway.common.service.judgmentin.JudgmentInService;
import com.beneway.common.service.putrule.PutRuleService;
import com.beneway.common.service.scheme.SchemeService;
import com.beneway.common.service.startagency.StartAgencyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MajorProjectServiceImpl extends ServiceImpl<MajorProjectDao, MajorProject> implements MajorProjectService, BaseService<MajorProject> {
    @Autowired
    MajorProjectDao majorProjectDao;

    @Autowired
    PutRuleService putRuleService;

    @Autowired
    JudgmentInService judgmentInService;
    
    @Autowired
    private SchemeService schemeService;

    @Autowired
    private EarlyWarnService earlyWarnService;

    @Autowired
    StartAgencyService startAgencyService;

    private MajorProjectService getCurrThis(){
        MajorProjectService currentProxy = (MajorProjectService) AopContext.currentProxy();
        return currentProxy;
    }


    @Override
    public Page<MajorProjectVo> queryPage(MajorProjectQueryFo majorProjectQueryFo){

        StatusEnum statusEnum = majorProjectQueryFo.getStatusEnum();
        //设置基础条件
        StatusEnum.setStatusProjectQuery(majorProjectQueryFo,statusEnum);

        //设置牵头单位条件
        startAgencyService.setProjectQuery(majorProjectQueryFo);

        Page page = PageUtils.getPage(majorProjectQueryFo);

        Page<MajorProjectVo> iPage = majorProjectDao.queryPage(page, majorProjectQueryFo);

        for (MajorProjectVo record : iPage.getRecords()) {
            String p = earlyWarnService.isHaveNoStandard(record.getId(), "P");
            if(StringUtils.isBlank(p)){
                String overdue = schemeService.isOverdue(record.getId());
                record.setOverdue(overdue);
            }else{
                record.setOverdue(p);
            }
        }
        return iPage;
    }

    public Result delete(MajorProject majorProject) {
        majorProject.setIsDelete(1);
        majorProjectDao.updateById(majorProject);
        return Result.ok();
    }

    @Override
    public MajorProjectVo getDetail(String id) {
        MajorProject m = getById(id);
        MajorProjectVo majorProjectVo = ClassUtil.toClass(m,MajorProjectVo.class);
        return majorProjectVo;
    }

    @Override
    @Transactional
    public Result ruku() {
        //找到总规则
        List<PutRule> list = putRuleService.list(new QueryWrapper<PutRule>().eq("type", "P").eq("pid", "0"));
        if(CollectionUtils.isEmpty(list)){
            return Result.ok("未找到相匹配的规则，入库0条");
        }
        //查找子规则
        List<String> ids = list.stream().map(res -> {return res.getId();}).collect(Collectors.toList());
        List<PutRule> ziRule = putRuleService.list(new QueryWrapper<PutRule>().in("pid", ids));
        if(CollectionUtils.isEmpty(ziRule)){
            return Result.ok("未找到相匹配的规则，入库0条");
        }
        List<String> content =  ziRule.stream().filter(res -> !res.getK().equals("project_grade") ).map(res -> {return res.getContent();}).collect(Collectors.toList());
        QueryWrapper<MajorProject> qw = new QueryWrapper<>();
        qw.isNull("project_status").isNull("project_grade").eq("is_delete" , "0");
        for (String s : content) {
            qw.apply(s);
        }
        List<MajorProject> m = list(qw);
        if(CollectionUtils.isEmpty(m)){
            return Result.ok("未找到相匹配的项目，入库0条");
        }
        //研判入库
        List<String> project_grade = ziRule.stream().filter(res -> res.getK().equals("project_grade")).map(res -> {
            return res.getV();
        }).collect(Collectors.toList());
        String type = "B";
        if(project_grade.contains("A")){
            type = "A";
        }
        try {
            List<JudgmentIn> j = new ArrayList<>();
            for (MajorProject majorProject : m) {
                JudgmentIn p = JudgmentIn.builder()
                        .assId(majorProject.getId())
                        .assType("P")
                        .judgmentGrade(type)
                        .judgmentUser(LoginUserUtils.getLoginUserId())
                        .judgmentTime(new Date())
                        .build();
                j.add(p);
                majorProject.setProjectGrade(type)
                        .setProjectStatus(StatusEnum.YRK.getStatus())
                        .setProjectStage("A");
                updateById(majorProject);
            }
            judgmentInService.saveBatch(j);
        }catch (Exception e){
            return Result.ok("错误，入库0条");
        }
        return Result.ok("成功入库" + m.size() + "条");
    }

    public void updateStage(StageEnum stageEnum,String assId){
            MajorProject majorProject = MajorProject.builder()
                    .id(assId)
                    .projectStage(stageEnum.getStage())
                    .build();
            getCurrThis().updateById(majorProject);
    }

    @Override
    public Result zfandsh() {
        Integer zf = majorProjectDao.zfsize();
        Integer sh = majorProjectDao.shsize();
        Map<String, Integer> res = new HashMap<>();
        res.put("zf" , zf);
        res.put("sh" , sh);
        return Result.ok(res);
    }


}
