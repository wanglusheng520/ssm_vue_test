package com.beneway.web.controller.majorindicators;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beneway.common.common.result.Result;
import com.beneway.common.entity.majorindicators.MajorIndicators;
import com.beneway.common.entity.majorindicators.fo.MajorIndicatorsFo;
import com.beneway.common.entity.majorindicators.fo.MajorIndicatorsQueryFo;
import com.beneway.common.entity.majorindicators.vo.MajorIndicatorsVo;
import com.beneway.common.service.majorindicators.MajorIndicatorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/majorIndicators")
public class MajorIndicatorsController {

    @Autowired
    private MajorIndicatorsService majorIndicatorsService;

    @PostMapping("/queryPage")
    public Result queryPage(@RequestBody MajorIndicatorsQueryFo majorIndicatorsFo){
        Page<MajorIndicatorsVo> page = majorIndicatorsService.queryPage(majorIndicatorsFo);
        return Result.ok(page);
    }

    /**
     * 添加评估指标
     * @param majorIndicatorsFo
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody MajorIndicatorsFo majorIndicatorsFo){
        return majorIndicatorsService.add(majorIndicatorsFo);
    }

    /**
     * 添加评估指标
     * @param majorIndicatorsFo
     * @return
     */
    @PostMapping("/addTarget")
    public Result addTarget(@RequestBody MajorIndicatorsFo majorIndicatorsFo){
        return majorIndicatorsService.addTarget(majorIndicatorsFo);
    }

    @GetMapping("/getOne")
    public Result getOne(MajorIndicators majorIndicators){
       return majorIndicatorsService.getOneDetail(majorIndicators);
    }

    @DeleteMapping("/")
    public Result del(MajorIndicators majorIndicators){
        return majorIndicatorsService.del(majorIndicators);
    }

    @PutMapping("/")
    public Result edit(@RequestBody MajorIndicators majorIndicators){
       return majorIndicatorsService.edit(majorIndicators);
    }

    @GetMapping("/selectCount")
    public Result selectCount(@RequestParam Map<String, Object> param){
        return Result.ok();
       // return majorIndicatorsService.selectCount(param);
    }

}
