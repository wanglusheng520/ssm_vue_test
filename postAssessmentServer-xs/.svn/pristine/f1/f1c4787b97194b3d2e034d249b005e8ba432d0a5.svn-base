package com.beneway.web.controller.system.login;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.beneway.common.common.utils.base.PageUtils;
import com.beneway.common.common.utils.jwt.LoginUserUtils;
import com.beneway.common.entity.system.Result;
import com.beneway.common.entity.system.login.Loginuser;
import com.beneway.common.service.system.login.LoginuserService;
import com.beneway.common.system.entity.sys_user.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class LoginuserController {

    @Autowired
    private LoginuserService loginuserService;

    /**
     * 获取用户
     */
    @GetMapping("/")
    public Result find(@RequestParam("id") String id) {
        Loginuser user = loginuserService.findById(id);
        user.setPassword(null);
        return Result.check(user);
    }

    /**
     * 获取当前登录用户信息
     * @return
     */
    @GetMapping("/getUserInfo")
    public Result getUserInfo(){
        LoginUserInfo user = LoginUserUtils.getLoginUserInfo();
        user.setPassword(null);
        return Result.success(user);
    }

    /**
     * 更新用户
     */
    @PutMapping("/")
    public Result update(@RequestBody Loginuser loginuser){
        return loginuserService.update(loginuser);
    }

    @PostMapping("/")
    public Result insert(@RequestBody Loginuser loginuser){
        return loginuserService.insert(loginuser);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id){
        return loginuserService.delete(id);
    }

    @DeleteMapping("/del")
    public Result del(String id){
        return loginuserService.del(id);
    }

    @PutMapping("/resetPWD")
    public Result resetPWD(String oldPWD, String newPWD, String checkPWD) {
        return loginuserService.resetPWD(LoginUserUtils.getLoginUserId(), oldPWD, newPWD, checkPWD);
    }

    @GetMapping("/getMyUsername")
    public Result getUsername(){
        LoginUserInfo user = LoginUserUtils.getLoginUserInfo();
        return Result.success(user.getUsername());
    }

    /*
        新的方法
     */

    /**
     * 用户管理分页查询
     * @param params
     * @return
     */
    @GetMapping("/queryPage")
    public Result queryPage(@RequestParam Map<String, Object> params){
        IPage<Loginuser> iPage = loginuserService.queryPage(params);
        return PageUtils.getPageResult(iPage);
    }


    /**
     * 用户管理分页查询
     * @param params
     * @return
     */
    @GetMapping("/queryPages")
    public Result queryPages(@RequestParam Map<String, Object> params){
        IPage<Loginuser> iPage = loginuserService.queryPages(params);
        return PageUtils.getPageResult(iPage);
    }
}
