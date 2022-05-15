package com.beneway.web.controller.system.dict;


import com.beneway.common.entity.system.Result;
import com.beneway.common.entity.system.dict.Dict;
import com.beneway.common.service.system.dict.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/dict")
public class DictController {
    @Autowired
    DictService dictService;

    /**
     * 主要用这个,传入neKey
     */
    @GetMapping("/getDictByKey")
    public Result getDictByKey(Dict dict){
        if (null==dict.getNeKey()){
            return Result.fail();
        }
        return dictService.getDictByKey(dict);
    }

    @GetMapping("/getParent")
    public Result getParent(Dict dict){
        return dictService.getParent(dict);
    }

    @GetMapping("/getSon")
    public Result getSon(Integer page,Integer limit,@RequestBody  Dict dict){
        System.out.println(dict);
        return dictService.getSon(page,limit,dict);
    }

    @GetMapping("/getSon1")
    public Result getSon(@RequestParam Map<String , Object> param){
        return dictService.getSon1(param);
    }

    @GetMapping("/")
    public Result find(Dict dict){
        return dictService.find(dict);
    }

    @PostMapping("/")
    public Result insert(@RequestBody  Dict dict){
        return dictService.insert(dict);
    }

    @PutMapping("/")
    public Result update(@RequestBody Dict dict){
        return dictService.update(dict);
    }

    @DeleteMapping("/")
    public Result delete(Dict dict){
        return dictService.delete(dict);
    }

    @DeleteMapping("/restore")
    public Result restore(Dict dict){
        return dictService.restore(dict);
    }

    @DeleteMapping("/realDel")
    public Result realDel(Dict dict){
        return dictService.realDel(dict);
    }

    @GetMapping("/getAllDelete")
    public Result getAllDelete(Integer page,Integer limit,Dict dict){
        return dictService.getAllDelete(page,limit,dict);
    }
}
