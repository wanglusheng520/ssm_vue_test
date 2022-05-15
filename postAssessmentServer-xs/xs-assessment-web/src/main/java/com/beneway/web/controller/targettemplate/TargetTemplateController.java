package com.beneway.web.controller.targettemplate;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beneway.common.common.result.Result;
import com.beneway.common.entity.targettemplate.TargetTemplate;
import com.beneway.common.entity.targettemplate.fo.TargetTemplateFo;
import com.beneway.common.entity.targettemplate.fo.TargetTemplateFoQuery;
import com.beneway.common.service.targettemplate.TargetTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/targetTemplate")
public class TargetTemplateController {

    @Autowired
    private TargetTemplateService targetTemplateService;

    @PostMapping("/queryPage")
    public Result queryPage(@RequestBody TargetTemplateFoQuery targetTemplateFoQuery){
        Page<TargetTemplate> page = targetTemplateService.queryPage(targetTemplateFoQuery);
        return Result.ok(page);
    }

    @PostMapping("/")
    public Result add(@RequestBody TargetTemplateFoQuery targetTemplate){
        return targetTemplateService.add(targetTemplate);
    }

//    @DeleteMapping("/")
//    public Result del(TargetTemplate  targetTemplate){
//        return targetTemplateService.del(targetTemplate);
//    }
//
    @GetMapping("/getOne")
    public Result getOne(TargetTemplate  targetTemplate){
        return targetTemplateService.getOneDetail(targetTemplate);
    }

    @PutMapping("/")
    public Result edit(@RequestBody TargetTemplateFo targetTemplate){
        return targetTemplateService.edit(targetTemplate);
    }

    @GetMapping("/allTemplateByType")
    public Result allTemplateByType(TargetTemplate  targetTemplate){
        return targetTemplateService.allTemplateByType(targetTemplate);
    }

//
//    @GetMapping("/detailTarget")
//    public Result detailTarget(TargetTemplate  targetTemplate){
//        return targetTemplateService.detailTarget(targetTemplate);
//    }
}
