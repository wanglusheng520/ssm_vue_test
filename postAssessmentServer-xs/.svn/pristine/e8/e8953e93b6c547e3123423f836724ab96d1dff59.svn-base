package com.beneway.web.controller.tzpromes;

import com.beneway.common.common.result.Result;
import com.beneway.common.entity.tzpromes.TzProMes;
import com.beneway.common.service.tzpromes.TzProMesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tzProMes")
public class TzProMesController {

    @Autowired
    private TzProMesService tzProMesService;

    @GetMapping("/detail")
    public Result detail(TzProMes tzProMes){
        return tzProMesService.detail(tzProMes);
    }

}
