package com.beneway.web.controller.earlywarn;


import com.beneway.common.common.result.Result;
import com.beneway.common.earlywarn.entity.EarlyWarn;
import com.beneway.common.earlywarn.service.EarlyWarnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/earlyWarn")
public class EarlyWarnController {

    @Autowired
    EarlyWarnService earlyWarnService;

    /**
     * 获取五条最新的预警信息
     * @return
     */
    @GetMapping("/earlyWarn")
    public Result earlyWarn(){
        return earlyWarnService.earlyWarn();
    }

    /**
     * 获取预警条数
     */
    @GetMapping("/")
    public Result earlyWarnCount(){
        return earlyWarnService.earlyWarnCount();
    }

    /**
     * 查询评估结果
     * @param earlyWarn
     * @return
     */
    @GetMapping("/assEarly")
    public Result assEarly(EarlyWarn earlyWarn){
        return earlyWarnService.assEarly(earlyWarn);
    }
}
