package com.beneway.web.controller.tzsjmes;


import com.beneway.common.entity.system.Result;
import com.beneway.common.entity.tzsjmes.TzSjMes;
import com.beneway.common.service.tzsjmes.TzSjMesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wj
 * @since 2022-03-19
 */
@RestController
@RequestMapping("/tzSjMes")
public class TzSjMesController {

    @Autowired
    private TzSjMesService tzSjMesService;

    @GetMapping("/")
    public Result sjMesByProjId(TzSjMes tzSjMes){
        return tzSjMesService.sjMesByProjId(tzSjMes);
    }

}
