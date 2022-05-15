package com.beneway.web.controller.prounitovertime;

import com.beneway.common.common.result.Result;
import com.beneway.common.service.prounitovertime.ProUnitOvertimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proUnitOvertime")
public class ProUnitOvertimeController {

    @Autowired
    private ProUnitOvertimeService proUnitOvertimeService;

    @GetMapping("/")
    public Result detailProUnitOverTime(String assId , String assType){
        return proUnitOvertimeService.detailProUnitOverTime(assId , assType);
    }

}
