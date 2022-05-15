package com.beneway.web.controller.system.router;


import com.beneway.common.entity.system.Result;
import com.beneway.common.entity.system.router.Router;
import com.beneway.common.service.system.router.RouterService;
import com.beneway.web.common.utils.jwt.PassToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangJun
 * @createTime 2021-09-06
 */
@RestController
@RequestMapping("/router")
public class RouterController {

    @Autowired
    private RouterService routerService;

    /**
     * 根据用户角色显示菜单
     * @return
     */
    @GetMapping("/")
    public Result router(Integer id){
        return routerService.router(id);
    }

    /**
     * 获取所有，用以角色编辑
     * @return
     */
    @GetMapping("/all")
    public Result allRouter(){
        return routerService.allRouter();
    }

    /**
     * 新增菜单
     */
    @PostMapping("/")
    public Result add(@RequestBody Router router){
        return routerService.add(router);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Result delete(Integer id){
        return routerService.delete(id);
    }

    @GetMapping("/getOne")
    public Result getOne(Router router){
        return routerService.getOne(router);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Router router){
        return routerService.update(router);
    }

}


