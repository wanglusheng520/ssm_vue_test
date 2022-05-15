package com.beneway.admin.controller.loginuser;

import cn.dev33.satoken.stp.StpUtil;
import com.beneway.admin.common.utils.LoginDecodeUtils;
import com.beneway.admin.service.loginuser.AdminLoginuserService;
import com.beneway.web.common.utils.jwt.PassToken;
import com.beneway.common.entity.system.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @email 2434017367@qq.com
 * @author: zhy
 * @date: 2021/9/17
 * @time: 17:13
 */
@RestController
@RequestMapping("/user")
public class AdminLoginuserController {

    @Autowired
    private AdminLoginuserService adminLoginuserService;

    /**
     * 获取登录验证图片
     * @return
     */
    @PassToken
    @GetMapping("/getLoginVerifyImg")
    public void getLoginVerifyImg(HttpServletResponse response){
        adminLoginuserService.getLoginVerifyImg(response);
    }

    /**
     * 用户登录方法
     */
    @PassToken
    @PostMapping("/login")
    public Result login(@RequestParam("account") String account,
                        @RequestParam("password") String password,
                        @RequestParam("verifyCode") String verifyCode){

        account = LoginDecodeUtils.decode(account);
        password = LoginDecodeUtils.decode(password);
        verifyCode = LoginDecodeUtils.decode(verifyCode);

        return adminLoginuserService.login(account, password, verifyCode);
    }

    /**
     *  pc扫码登录
     */
    @PassToken
    @PostMapping("/zwddQrcodeLogin")
    public Result zwddQrcodeLogin(@RequestParam("code") String code){
        return adminLoginuserService.zwddQrcodeLogin(code);
    }

    /**
     * 数智杭州登录
     * @param code
     * @return
     */
    @PassToken
    @PostMapping("/szhzLogin")
    public Result szhzLogin(@RequestParam("code") String code){
        return adminLoginuserService.szhzLogin(code);
    }

    /**
     *  pc浙政钉进入
     */
    @PassToken
    @PostMapping("/login_ddPCzzd")
    public Result login_ddPCzzd(String auth_code){
        return adminLoginuserService.login_ddPCzzd(auth_code);
    }

    @GetMapping("/logout")
    public Result logout(){
        StpUtil.logout();
        return Result.success();
    }

    /**
     * 获取浙政钉扫码，添加用户信息
     * @param code
     * @return
     */
    @GetMapping("/getAddUserInfoByQrcode")
    public Result getAddUserInfoByQrcode(@RequestParam("code") String code){
        return adminLoginuserService.getAddUserInfoByQrcode(code);
    }

    /**
     * 根据手机号获取添加用户信息
     * @param mobile
     * @return
     */
    @GetMapping("/getAddUserInfoByMobile")
    public Result getAddUserInfoByMobile(@RequestParam("mobile") String mobile){
        return adminLoginuserService.getAddUserInfoByMobile(mobile);
    }

}
