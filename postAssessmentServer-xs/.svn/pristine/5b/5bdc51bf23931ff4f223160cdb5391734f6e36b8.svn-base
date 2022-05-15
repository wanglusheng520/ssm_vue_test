package com.beneway.web.controller.startagency;


import com.beneway.common.common.result.Result;
import com.beneway.common.entity.startagency.StartAgency;
import com.beneway.common.entity.startagency.fo.StartAgencyFo;
import com.beneway.common.entity.startagency.fo.StartAgencyQueryFo;
import com.beneway.common.service.startagency.StartAgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/startAgency")
public class StartAgencyController {

    @Autowired
    private StartAgencyService startAgencyService;

    @PostMapping("/queryPage")
    public Result queryPage(@RequestBody StartAgencyQueryFo startAgencyQueryFo){
        return Result.ok(startAgencyService.queryPage(startAgencyQueryFo));
    }

    @DeleteMapping("/del")
    public Result del(@RequestParam String id){
        return startAgencyService.del(id);
    }

    @PostMapping("/addOrUpdate")
    public Result addOrUpdate(@RequestBody StartAgencyFo startAgencyFo){
       return startAgencyService.addOrUpdate(startAgencyFo);
    }

    @GetMapping("/getById")
    public Result getById(@RequestParam String id){
        return startAgencyService.getById(id);
    }

}
