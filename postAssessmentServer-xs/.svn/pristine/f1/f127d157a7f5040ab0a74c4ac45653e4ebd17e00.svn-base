package com.beneway.web.controller.assessstart;

import com.beneway.common.common.result.Result;
import com.beneway.common.entity.assessstart.AssessStart;
import com.beneway.common.entity.majorproject.MajorProject;
import com.beneway.common.service.assessstart.AssessStartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assessStart")
public class AssessStartController {

    @Autowired
    private AssessStartService assessStartService;


    /**
     * 获取方案牵头单位和协同单位
     * @param AssessStart
     * @return
     */
    @GetMapping("/getUnit")
    public Result getUnit(AssessStart AssessStart){
        return assessStartService.getUnit(AssessStart);
    }

    /**
     * 详情页的评估进度 其中评估信息
     * @param assessStart
     * @return
     */
    @GetMapping("/getDetail")
    public Result getDetail(AssessStart assessStart){
        return assessStartService.getDetail(assessStart);
    }

    @GetMapping("/")
    public Result myParticipation(){
        return assessStartService.myParticipation();
    }

}
