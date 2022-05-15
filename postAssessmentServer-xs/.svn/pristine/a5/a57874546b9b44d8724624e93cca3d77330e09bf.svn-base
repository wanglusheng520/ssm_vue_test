package com.beneway.common.service.normativedoc;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.base.BaseService;
import com.beneway.common.common.enums.StageEnum;
import com.beneway.common.common.enums.StatusEnum;
import com.beneway.common.common.utils.ClassUtil;
import com.beneway.common.common.utils.jwt.LoginUserUtils;
import com.beneway.common.common.utils.page.PageUtils;
import com.beneway.common.dao.normativedoc.NormativeDocDao;
import com.beneway.common.earlywarn.service.EarlyWarnService;
import com.beneway.common.entity.judgmentin.JudgmentIn;
import com.beneway.common.entity.majorproject.vo.MajorProjectVo;
import com.beneway.common.entity.normativedoc.NormativeDoc;
import com.beneway.common.entity.normativedoc.fo.NormativeDocQueryFo;
import com.beneway.common.entity.normativedoc.vo.NormativeDocVo;
import com.beneway.common.entity.putrule.PutRule;
import com.beneway.common.entity.scheme.Scheme;
import com.beneway.common.entity.system.Result;
import com.beneway.common.service.judgmentin.JudgmentInService;
import com.beneway.common.service.majorproject.MajorProjectService;
import com.beneway.common.service.putrule.PutRuleService;
import com.beneway.common.service.scheme.SchemeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NormativeDocServiceImp extends ServiceImpl<NormativeDocDao, NormativeDoc> implements NormativeDocService, BaseService<NormativeDoc> {
    @Autowired
    NormativeDocDao normativeDocDao;

    @Autowired
    private PutRuleService putRuleService;

    @Autowired
    private JudgmentInService judgmentInService;

    @Autowired
    private EarlyWarnService earlyWarnService;

    @Autowired
    private SchemeService schemeService;

    private NormativeDocService getCurrThis(){
        NormativeDocService currentProxy = (NormativeDocService) AopContext.currentProxy();
        return currentProxy;
    }

    @Override
    public Page<NormativeDocVo> queryPage(NormativeDocQueryFo normativeDocQueryFo){

        StatusEnum statusEnum = normativeDocQueryFo.getStatusEnum();

        //设置基础条件
        StatusEnum.setStatusNormativeDocQuery(normativeDocQueryFo,statusEnum);

        Page page = PageUtils.getPage(normativeDocQueryFo);
        Page<NormativeDocVo> iPage = normativeDocDao.queryPage(page, normativeDocQueryFo);

        for (NormativeDocVo record : iPage.getRecords()) {
            String p = earlyWarnService.isHaveNoStandard(record.getId(), "N");
            if(StringUtils.isBlank(p)) {
                String overdue = schemeService.isOverdue(record.getId());
                record.setOverdue(overdue);
            }else{
                record.setOverdue(p);
            }
        }
        return iPage;
    }

    public Result delete(NormativeDoc normativeDoc){
        normativeDoc.setIsDelete(1);
        normativeDocDao.updateById(normativeDoc);
        return Result.success();
    }

    @Override
    public Result ruku() {
        //找到总规则
        List<PutRule> list = putRuleService.list(new QueryWrapper<PutRule>().eq("type", "N").eq("pid", "0"));
        if(CollectionUtils.isEmpty(list)){
            return Result.success("未找到相匹配的规则，入库0条");
        }
        //查找子规则
        List<String> ids = list.stream().map(res -> {return res.getId();}).collect(Collectors.toList());
        List<PutRule> ziRule = putRuleService.list(new QueryWrapper<PutRule>().in("pid", ids));
        if(CollectionUtils.isEmpty(ziRule)){
            return Result.success("未找到相匹配的规则，入库0条");
        }
        List<String> content =  ziRule.stream().filter(res -> !res.getK().equals("project_grade") ).map(res -> {return res.getContent();}).collect(Collectors.toList());
        QueryWrapper<NormativeDoc> qw = new QueryWrapper<>();
        qw.isNull("normative_status").isNull("normative_grade").eq("is_delete" , "0");
        for (String s : content) {
            qw.apply(s);
        }
        List<NormativeDoc> m = list(qw);
        if(CollectionUtils.isEmpty(m)){
            return Result.success("未找到相匹配的项目，入库0条");
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
            for (NormativeDoc normativeDoc : m) {
                JudgmentIn p = JudgmentIn.builder()
                        .assId(normativeDoc.getId())
                        .assType("P")
                        .judgmentGrade(type)
                        .judgmentUser(LoginUserUtils.getLoginUserId())
                        .judgmentTime(new Date())
                        .build();
                j.add(p);
                normativeDoc.setNormativeGrade(type)
                        .setNormativeStatus(StatusEnum.YRK.getStatus())
                        .setNormativeStage("A");
                updateById(normativeDoc);
            }
            judgmentInService.saveBatch(j);
        }catch (Exception e){
            return Result.success("错误，入库0条");
        }
        return Result.success("成功入库" + m.size() + "条");
    }

    @Override
    public NormativeDocVo getDetail(String id) {
        NormativeDoc normativeDoc = getById(id);
        NormativeDocVo normativeDocVo = ClassUtil.toClass(normativeDoc,NormativeDocVo.class);
        return normativeDocVo;
    }



    public void updateStage(StageEnum stageEnum, String assId){
        NormativeDoc normativeDoc = NormativeDoc.builder()
                .id(assId)
                .normativeStage(stageEnum.getStage())
                .build();
        getCurrThis().updateById(normativeDoc);
    }

    @Override
    public com.beneway.common.common.result.Result amount() {
        Integer i = normativeDocDao.amount();
        Map<String, Integer> res = new HashMap<>();
        res.put("gfAmount" , i);
        return com.beneway.common.common.result.Result.ok(res);
    }

}
