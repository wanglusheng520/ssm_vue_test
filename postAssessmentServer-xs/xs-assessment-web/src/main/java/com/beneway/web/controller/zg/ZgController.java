package com.beneway.web.controller.zg;

import com.beneway.common.common.enums.StageEnum;
import com.beneway.common.common.result.Result;
import com.beneway.common.entity.zg.Zg;
import com.beneway.common.service.zg.ZgService;
import com.beneway.web.common.annotation.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 整改
 */
@RestController
@RequestMapping("/zg")
public class ZgController {

    @Autowired
    private ZgService zgService;

    @Log(stage = StageEnum.Z_WAIT_RECTIFICATION)
    @PostMapping("/")
    public Result add(@RequestBody Zg zg){
        return zgService.add(zg);
    }

    /**
     * 根据项目获得整改信息
     * @param zg
     * @return
     */
    @GetMapping("/")
    public Result zgContentByProject(Zg zg){
        return zgService.zgContentByProject(zg);
    }

    /**
     * 获取我的整改信息
     * @param zg
     * @return
     */
    @GetMapping("/myZgMessage")
    public Result myZgMessage(Zg zg){
        return zgService.myZgMessage(zg);
    }

}


