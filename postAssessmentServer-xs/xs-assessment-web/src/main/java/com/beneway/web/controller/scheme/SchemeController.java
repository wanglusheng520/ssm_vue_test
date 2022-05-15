package com.beneway.web.controller.scheme;

import com.beneway.common.common.enums.StageEnum;
import com.beneway.common.common.result.Result;
import com.beneway.common.entity.scheme.Scheme;
import com.beneway.common.entity.scheme.fo.SchemeFo;
import com.beneway.common.entity.scheme.vo.SchemeListFo;
import com.beneway.common.entity.scheme.vo.SchemeVo;
import com.beneway.common.service.scheme.SchemeService;
import com.beneway.web.common.annotation.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scheme")
public class SchemeController {

    @Autowired
    private SchemeService schemeService;


    /**
     * 制定方案
     * @return
     */
    @Log(stage = StageEnum.C_WAIT_EVALUATED)
    @PostMapping("/addScheme")
    public Result addScheme(@RequestBody SchemeListFo schemeListFo){
        return schemeService.addScheme(schemeListFo);
    }



    /**
     * 查询填报的指标
     * @param scheme
     * @return
     */
    @GetMapping("/getTarget")
    public Result getTarget(Scheme scheme){
        return schemeService.getTarget(scheme);
    }

    /**
     * 指标填报
     * @param scheme
     * @return
     */
    @Log(stage = StageEnum.B_FILLING_IN)
    @PostMapping("/zbtb")
    public Result zbtb(@RequestBody SchemeVo scheme){
        return schemeService.zbtb(scheme);
    }


    /**
     * 获取方案详细指标
     */
    @GetMapping("/getSchemeTargetDetailNew")
    public Result getSchemeTargetDetailNew(SchemeFo schemeFo){
        return schemeService.getSchemeTargetDetailNew(schemeFo);
    }




}
