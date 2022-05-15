package com.beneway.web.controller.choosable;

import com.beneway.common.common.result.Result;
import com.beneway.common.service.choosable.ChoosableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/choosable")
public class ChoosableController {

    @Autowired
    private ChoosableService choosableService;

    @GetMapping("/")
    public Result list(){
        return Result.ok(choosableService.list());
    }

}
