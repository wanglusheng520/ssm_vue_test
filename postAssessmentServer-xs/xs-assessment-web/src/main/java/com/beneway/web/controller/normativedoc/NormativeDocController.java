package com.beneway.web.controller.normativedoc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beneway.common.common.result.Result;
import com.beneway.common.entity.majorproject.vo.MajorProjectVo;
import com.beneway.common.entity.normativedoc.NormativeDoc;
import com.beneway.common.entity.normativedoc.fo.NormativeDocQueryFo;
import com.beneway.common.entity.normativedoc.vo.NormativeDocVo;
import com.beneway.common.service.normativedoc.NormativeDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/normativeDoc")
public class NormativeDocController {
    @Autowired
    NormativeDocService normativeDocService;


    /**
     * 分页/数据管理
     */
    @PostMapping("/queryPage")
    public Result queryPage(@RequestBody NormativeDocQueryFo normativeDocQueryFo){
        Page<NormativeDocVo> page = normativeDocService.queryPage(normativeDocQueryFo);
        return Result.ok(page);
    }

    /**
     * 逻辑删除
     * @param normativeDoc
     * @return
     */
    @PostMapping("/delete")
    public Result delete(NormativeDoc normativeDoc){
        //return normativeDocService.delete(normativeDoc);
        return Result.ok();
    }

    @GetMapping("/ruku")
    public Result ruku(){
        return Result.ok();
        //return normativeDocService.ruku();
    }

    /**
     * 获取详情
     * @param
     * @return
     */
    @GetMapping("/getDetail")
    public Result getDetail(@RequestParam String id){
       return Result.ok(normativeDocService.getDetail(id));
    }

    /**
     * 规范新性文件数
     * @return
     */
    @GetMapping("/amount")
    public Result amount(){
        return normativeDocService.amount();
    }

}
