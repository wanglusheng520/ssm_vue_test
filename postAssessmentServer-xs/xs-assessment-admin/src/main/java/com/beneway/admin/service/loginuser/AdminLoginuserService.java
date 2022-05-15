package com.beneway.admin.service.loginuser;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.entity.system.Result;
import com.beneway.common.entity.system.login.Loginuser;
import com.beneway.web.common.utils.jwt.PassToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @email 2434017367@qq.com
 * @author: zhy
 * @date: 2021/9/17
 * @time: 17:14
 */
public interface AdminLoginuserService extends IService<Loginuser> {

    /**
     * 获取登录验证图片
     * @return
     */
    void getLoginVerifyImg(HttpServletResponse response);

    Result login(String account, String password, String verifyCode);

    Result zwddQrcodeLogin(String code);

    /**
     * 数智杭州登录
     * @param code
     * @return
     */
    Result szhzLogin(String code);

    Result login_ddPCzzd(String auth_code);

    /**
     * 获取浙政钉扫码，添加用户信息
     * @param code
     * @return
     */
    Result getAddUserInfoByQrcode(String code);

    /**
     * 根据手机号获取添加用户信息
     * @param mobile
     * @return
     */
    Result getAddUserInfoByMobile(String mobile);

}
