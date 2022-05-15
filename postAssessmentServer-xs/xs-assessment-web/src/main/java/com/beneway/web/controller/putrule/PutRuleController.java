package com.beneway.web.controller.putrule;

import com.beneway.common.common.result.Result;
import com.beneway.common.entity.putrule.PutRule;
import com.beneway.common.entity.putrule.fo.PutRuleFo;
import com.beneway.common.entity.putrule.fo.PutRuleQueryFo;
import com.beneway.common.service.putrule.PutRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/putRule")
public class PutRuleController {

    @Autowired
    private PutRuleService putRuleService;

//    @GetMapping("/queryPage")
//    public Result queryPage(@RequestParam Map<String , Object> param){
//        return putRuleService.queryPage(param);
//    }

    @PostMapping("/queryPage")
    public Result queryPage(@RequestBody PutRuleQueryFo putRuleQueryFo){
        return Result.ok(putRuleService.queryPage(putRuleQueryFo));
    }

    @PostMapping("/add")
    public Result add(@RequestBody PutRuleFo putRuleFo){
        return putRuleService.add(putRuleFo);
    }

    @GetMapping("/getOne")
    public Result getOne(PutRule putRule){
        //return putRuleService.getOneDetail(putRule);
        return Result.ok();
    }

    @DeleteMapping("/")
    public Result del(PutRule putRule){
       // return putRuleService.del(putRule);
        return Result.ok();
    }

    @PutMapping("/")
    public Result edit(@RequestBody PutRule putRule){
        //return putRuleService.edit(putRule);
        return Result.ok();
    }

    @PutMapping("/isWork")
    public Result isWork(@RequestBody PutRule putRule){
        return Result.ok();
        //return putRuleService.isWork(putRule);
    }

}
