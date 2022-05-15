package com.beneway.web.controller.detailtarget;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beneway.common.common.result.Result;
import com.beneway.common.entity.detailtarget.DetailTarget;
import com.beneway.common.entity.detailtarget.fo.DetailTargetFo;
import com.beneway.common.entity.detailtarget.fo.DetailTargetQueryFo;
import com.beneway.common.entity.detailtarget.vo.DetailTargetVo;
import com.beneway.common.entity.majorindicators.fo.MajorIndicatorsQueryFo;
import com.beneway.common.entity.majorindicators.vo.MajorIndicatorsVo;
import com.beneway.common.service.detailtarget.DetailTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/detailTarget")
public class DetailTargetController {


    @Autowired
    private DetailTargetService detailTargetService;

    /**
     * 删除
     * @param detailTarget
     * @return
     */
    @DeleteMapping("/")
    public Result del(DetailTarget detailTarget){
        return detailTargetService.del(detailTarget);
    }

    @PostMapping("/add")
    public Result add(@RequestBody DetailTargetFo detailTargetFo){
        return detailTargetService.add(detailTargetFo);
    }

    /**
     * 添加详细指标
     * @param detailTargetFo
     * @return
     */
    @PostMapping("/")
    public Result addDetail(@RequestBody DetailTargetFo detailTargetFo){
        return detailTargetService.addDetail(detailTargetFo);
    }

    @PostMapping("/selectDetailTargetForIds")
    public Result selectDetailTargetForIds(@RequestBody DetailTargetFo detailTargetFo){
        return detailTargetService.selectDetailTargetForIds(detailTargetFo);
    }

    @GetMapping("/allTarget")
    public Result allTarget(){
        return detailTargetService.allTarget();
    }



    @PostMapping("/queryPage")
    public Result queryPage(@RequestBody DetailTargetQueryFo detailTargetQueryFo){
        Page<DetailTargetVo> page = detailTargetService.queryPage(detailTargetQueryFo);
        return Result.ok(page);
    }

}
