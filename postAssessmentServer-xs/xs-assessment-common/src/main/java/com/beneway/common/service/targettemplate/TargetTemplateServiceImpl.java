package com.beneway.common.service.targettemplate;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.common.result.Result;
import com.beneway.common.common.utils.jwt.LoginUserUtils;
import com.beneway.common.common.utils.page.PageUtils;
import com.beneway.common.dao.targettemplate.TargetTemplateDao;
import com.beneway.common.entity.detailtarget.fo.DetailTargetFo;
import com.beneway.common.entity.targettemplate.TargetTemplate;
import com.beneway.common.entity.targettemplate.fo.TargetTemplateFo;
import com.beneway.common.entity.targettemplate.fo.TargetTemplateFoQuery;
import com.beneway.common.service.detailtarget.DetailTargetService;
import com.beneway.common.service.majorindicators.MajorIndicatorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TargetTemplateServiceImpl extends ServiceImpl<TargetTemplateDao , TargetTemplate> implements TargetTemplateService {

    @Autowired
    private TargetTemplateDao targetTemplateDao;

    @Autowired
    private DetailTargetService detailTargetService;

    @Autowired
    private MajorIndicatorsService majorIndicatorsService;

    @Override
    public Result add(TargetTemplateFoQuery targetTemplate) {
        TargetTemplate tt = TargetTemplate.builder()
                .createTime(new Date())
                .createUser(LoginUserUtils.getLoginUserId())
                .type(targetTemplate.getType())
                .templateName(targetTemplate.getTemplateName())
                .templateRemark(targetTemplate.getTemplateRemark())
                .build();
        List<DetailTargetFo> detailTargetFos = targetTemplate.getDetailTargetFos();
        if(!detailTargetFos.isEmpty()){
            String s = JSON.toJSONString(detailTargetFos);
            tt.setTarget(s);
        }
        //添加指标模板
        save(tt);
        return Result.ok();
    }

    @Override
    public Page<TargetTemplate> queryPage(TargetTemplateFoQuery targetTemplateFoQuery) {
        Page page = PageUtils.getPage(targetTemplateFoQuery);
        Page<TargetTemplate> iPage = targetTemplateDao.queryPage(page , targetTemplateFoQuery);
        return iPage;
    }
//
//    @Override
//    public Result del(TargetTemplate targetTemplate) {
//        targetTemplateDao.deleteById(targetTemplate.getId());
//        return Result.success();
//    }
//
    @Override
    public Result getOneDetail(TargetTemplate targetTemplate) {
        targetTemplate = getById(targetTemplate.getId());
        String target = targetTemplate.getTarget();
        List<DetailTargetFo> d = new ArrayList<>();
        try{
            d = JSON.parseArray(target, DetailTargetFo.class);
        }catch (Exception e){
            log.error("指标列表转换错误");
        }
        if(d.isEmpty()){
            return Result.ok(targetTemplate);
        }
        TargetTemplateFoQuery build = TargetTemplateFoQuery.builder()
                    .detailTargetFos(d)
                .type(targetTemplate.getType())
                .templateName(targetTemplate.getTemplateName())
                .templateRemark(targetTemplate.getTemplateRemark())
                .build();
        return Result.ok(build);
    }

    @Override
    public Result edit(TargetTemplateFo targetTemplate) {
        List<DetailTargetFo> detailTargetFos = targetTemplate.getDetailTargetFos();
        if(!detailTargetFos.isEmpty()){
            String s = JSON.toJSONString(detailTargetFos);
            targetTemplate.setTarget(s);
        }
        updateById(targetTemplate);
        return Result.ok();
    }

    @Override
    public Result allTemplateByType(TargetTemplate targetTemplate) {
        List<TargetTemplate> type = list(new QueryWrapper<TargetTemplate>().eq("type", targetTemplate.getType()));
        if (type.isEmpty()){
            return Result.ok();
        }
        List<TargetTemplateFoQuery> t = new ArrayList<>();
        for (TargetTemplate template : type) {
            List<DetailTargetFo> d;
            try{
                d = JSON.parseArray(template.getTarget(), DetailTargetFo.class);
            }catch (Exception e){
                log.error("指标列表转换错误");
                continue;
            }
            if(d.isEmpty()){
                continue;
            }
            TargetTemplateFoQuery build = TargetTemplateFoQuery.builder()
                    .id(template.getId())
                    .detailTargetFos(d)
                    .type(template.getType())
                    .templateName(template.getTemplateName())
                    .templateRemark(template.getTemplateRemark())
                    .build();
            t.add(build);
        }
        return Result.ok(t);
    }

//    @Override
//    public Result detailTarget(TargetTemplate targetTemplate) {
//        targetTemplate = getById(targetTemplate.getId());
//        String targetId = targetTemplate.getTargetId();
//        if(StringUtils.isEmpty(targetId)){
//            return Result.success();
//        }
//        List<String> ids = Arrays.asList(targetId.split(","));
//        List<DetailTarget> list = detailTargetService.list(new QueryWrapper<DetailTarget>().in("id", ids));
//        if(CollectionUtils.isEmpty(list)){
//            return Result.success();
//        }
//        //获取大指标的id
//        List<String> collect = list.stream().map(res -> {
//            return res.getMajorIndicatorsId();
//        }).distinct().collect(Collectors.toList());
//        List<MajorIndicators> m = majorIndicatorsService.list(new QueryWrapper<MajorIndicators>().in("id", collect).select("id", "target_title"));
//        if(CollectionUtils.isEmpty(m)){
//            return Result.success();
//        }
//        for (MajorIndicators majorIndicators : m) {
//            List<DetailTarget> res = new ArrayList<>();
//            for (DetailTarget s : list) {
//                if(majorIndicators.getId().equals(s.getMajorIndicatorsId())){
//                    res.add(s);
//                }
//            }
//            majorIndicators.setDetailTarget(res);
//        }
//        return Result.success();
//    }
}
