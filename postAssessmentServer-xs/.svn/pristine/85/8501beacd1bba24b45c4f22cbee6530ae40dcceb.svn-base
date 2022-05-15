package com.beneway.web.controller.majorproject;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beneway.common.common.result.Result;
import com.beneway.common.entity.majorproject.MajorProject;
import com.beneway.common.entity.majorproject.fo.MajorProjectQueryFo;
import com.beneway.common.entity.majorproject.vo.MajorProjectVo;
import com.beneway.common.service.majorproject.MajorProjectService;
import com.beneway.web.common.annotation.ReqApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/majorProject")
public class MajorProjectController {

    @Autowired
    private MajorProjectService majorProjectService;

//    @ReqApi(value = "重大投资项目分页查询", permission = "project:queryPage")
    @PostMapping("/queryPage")
    public Result queryPage(@RequestBody MajorProjectQueryFo majorProjectQueryFo){
        Page<MajorProjectVo> page = majorProjectService.queryPage(majorProjectQueryFo);
        return Result.ok(page);
    }

    /**
     * 逻辑删除
     * @param majorProject
     * @return
     */
    @PostMapping("/delete")
    public Result delete(MajorProject majorProject){
        //return majorProjectService.delete(majorProject);
        return Result.ok();
    }

    /**
     * 获取详情
     * @param
     * @return
     */
    @GetMapping("/getDetail")
    public Result getDetail(@RequestParam String id){
        return Result.ok(majorProjectService.getDetail(id));
    }

    @GetMapping("/ruku")
    public Result ruku(){
        return majorProjectService.ruku();
    }

    /**
     * 获取政府投资项目数 和 社会投资项目数
     * @return
     */
    @GetMapping("/zfandsh")
    public Result zfandsh(){
        return majorProjectService.zfandsh();
    }
}
