package com.beneway.admin.service.loginuser;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.common.enums.UserloginLogTypeEnum;
import com.beneway.web.common.utils.IPUtil;
import com.beneway.common.common.utils.PYUtils;
import com.beneway.common.common.utils.dd.ZWDDPhoneUtils;
import com.beneway.common.common.utils.dd.ZWDDQrcodeUtils;
import com.beneway.common.common.utils.dd.ZWDDUtils;
import com.beneway.common.common.utils.redis.RedisUtils;
import com.beneway.common.dao.system.login.LoginuserDao;
import com.beneway.common.entity.system.Result;
import com.beneway.common.entity.system.login.Loginuser;
import com.beneway.common.service.system.login.LoginuserService;
import com.beneway.web.service.WebLoginuserService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @email 2434017367@qq.com
 * @author: zhy
 * @date: 2021/9/17
 * @time: 17:16
 */
@Log4j2
@Service("adminLoginuserService")
public class AdminLoginuserServiceImpl extends ServiceImpl<LoginuserDao, Loginuser> implements AdminLoginuserService {

    @Autowired
    private LoginuserDao loginuserDao;

    @Autowired
    private LoginuserService loginuserService;

    @Autowired
    private WebLoginuserService webLoginuserService;

    @Autowired
    private ZWDDUtils zwddUtils;

    @Autowired
    private ZWDDQrcodeUtils zwddQrcodeUtils;

    @Autowired
    private ZWDDPhoneUtils zwddPhoneUtils;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 获取登录验证图片
     * @return
     */
    @Override
    public void getLoginVerifyImg(HttpServletResponse response){
        //定义图形验证码的长、宽、验证码字符数、干扰元素个数
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(180, 60, 4, 100);
        //图形验证码写出，可以写出到文件，也可以写出到流
        try {
            lineCaptcha.write(response.getOutputStream());
            // 添加到redis缓存中
            String code = lineCaptcha.getCode();
            String key = getLoginVerifyRedisCodeKey();
            redisUtils.set(key, code, DateUnit.MINUTE.getMillis() * 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getLoginVerifyRedisCodeKey(){
        String ip = IPUtil.getIpAddr();
        String key = "loginVerifyCode" + ip;
        return key;
    }

    /**
     * 登录功能，脱敏
     */
    @Override
    public Result login(String account, String password, String verifyCode){
        // 判断验证码是否正确
        String key = getLoginVerifyRedisCodeKey();
        String code = redisUtils.get(key, 0);
        if (!verifyCode.equalsIgnoreCase(code)){
            return Result.fail("验证码错误");
        }

        // 记录账号密码错误次数一天不能多于10次
        long loginNumOutTime = DateUnit.HOUR.getMillis() * 24;
        String loginNumRedisKey = getLoginNumRedisKey(account);
        String loginNumStr = redisUtils.get(loginNumRedisKey, loginNumOutTime);
        if (StrUtil.isNotEmpty(loginNumStr)){
            int loginNum = Integer.parseInt(loginNumStr);
            if (loginNum > 10){
                return Result.fail("密码错误超过10次账号已被冻结24小时，联系管理员进行解冻");
            }
        }

        Loginuser o = new Loginuser();
        o.setAccount(account);
        o.setPassword(password);
        Loginuser u =  loginuserDao.login(o);
        if (null != u){
            // 登录成功
            if (StrUtil.isNotEmpty(loginNumStr)){
                redisUtils.delete(loginNumRedisKey);
            }

            String token = webLoginuserService.login(u, UserloginLogTypeEnum.ACCOUNT);
            return Result.success(token);
        }else {
            int loginNum = 0;
            if (StrUtil.isNotEmpty(loginNumStr)){
                loginNum = Integer.parseInt(loginNumStr);
            }
            loginNum += 1;
            redisUtils.set(loginNumRedisKey, loginNum, loginNumOutTime);

            return Result.fail().setMsg("账号或密码错误");
        }
    }

    private String getLoginNumRedisKey(String account){
        String key = "loginNum" + account;
        return key;
    }

    /**
     * pc扫码登陆
     * @param code
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result zwddQrcodeLogin(String code){
        JsonObject data = zwddQrcodeUtils.getUserInfoQrcode(code);
        if(null != data){
            String accountId = data.get("accountId").getAsString();
            Loginuser loginuser = getByAccountId(accountId);
            if (loginuser != null){
                upTenantId(data);
                String token = webLoginuserService.login(loginuser, UserloginLogTypeEnum.QRCODE);
                return Result.success(token);
            }else{
                return Result.fail("当前用户未在本系统注册，请联系系统管理员");
            }
        }else{
            return Result.fail("权限不足，扫码登录失败").setCode(1000);
//            return Result.success();
        }
    }

    /**
     * 数智杭州登录
     * @param code
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result szhzLogin(String code) {
        String url = "https://szhz.hangzhou.gov.cn:8027/szhz/system/getAccessTokenUser?accessToken=" + code;
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken", code);
        String json = restTemplate.getForObject(url, String.class);
        log.info("数智杭州获取浙政钉用户信息：{}", json);
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        boolean success = object.get("success").getAsBoolean();
        if (success){
            JsonObject data = object.get("data").getAsJsonObject();
            String extUserId = data.get("extUserId").getAsString();
            Loginuser loginuser = getByAccountId(extUserId);
            if (loginuser != null){
                String token = webLoginuserService.login(loginuser, UserloginLogTypeEnum.SZHZ);
                return Result.success(token);
            }else{
                return Result.fail("当前用户未在本系统注册，请联系系统管理员");
            }
        }else{
            log.error("数智杭州获取浙政钉用户信息失败");
            return Result.fail("数智杭州获取浙政钉用户信息失败");
        }
    }

    /**
     * 浙政钉钉直接登陆
     * @param auth_code
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result login_ddPCzzd(String auth_code){
        JsonObject data = zwddUtils.getUserInfo(auth_code);
        if(null != data){
            String accountId = data.get("accountId").getAsString();
            Loginuser loginuser = getByAccountId(accountId);
            if (loginuser != null){
                upTenantId(data);

                String token = webLoginuserService.login(loginuser, UserloginLogTypeEnum.PC);

                Map<String, String> map = new HashMap<>();
                map.put("token", token);
                map.put("accountId", accountId);
                return Result.success(map);
            }else{
                return Result.fail("当前用户未在本系统注册，请联系系统管理员");
            }
        }else{
            return Result.fail("未获取到浙政钉用户信息，请联系系统管理员");
        }
    }

    private Loginuser getByAccountId(String accountId){
        Loginuser loginuser = this.getOne(new LambdaQueryWrapper<Loginuser>().eq(Loginuser::getAccountId, accountId));
        return loginuser;
    }

    /**
     * 更新租户id
     * @param jsonObject
     */
    private void upTenantId(JsonObject jsonObject){
        String accountId = jsonObject.get("accountId").getAsString();
        String tenantId = jsonObject.get("tenantId").getAsString();

        this.update(new LambdaUpdateWrapper<Loginuser>()
                .set(Loginuser::getTenantId, tenantId)
                .eq(Loginuser::getAccountId, accountId)
                .isNull(Loginuser::getTenantId));
    }

    /**
     * 获取浙政钉扫码，添加用户信息
     * @param code
     * @return
     */
    @Override
    public Result getAddUserInfoByQrcode(String code){
        JsonObject data = zwddQrcodeUtils.getUserInfoQrcode(code);
        Loginuser loginuser = new Loginuser();
        // 获取用户名
        String lastName = data.get("lastName").getAsString();
        loginuser.setUsername(lastName);

        return packAddUserInfo(loginuser, data);
    }

    /**
     * 根据手机号获取添加用户信息
     * @param mobile
     * @return
     */
    @Override
    public Result getAddUserInfoByMobile(String mobile) {
        JsonObject data = zwddPhoneUtils.getUserInfoByMobile(mobile);
        Loginuser loginuser = new Loginuser();
        // 获取用户名
        String lastName = data.get("employeeName").getAsString();
        loginuser.setUsername(lastName);

        return packAddUserInfo(loginuser, data);
    }

    private Result packAddUserInfo(Loginuser loginuser, JsonObject data){
        String username = loginuser.getUsername();
        String namePy = PYUtils.hanyuToPinyinFirst(username);
        for (int i = 0; i < 5; i++) {
            // 根据用户名生成账号
            int number = (int) (Math.random() * 8999 + 1000);
            String account = namePy + number;
            loginuser.setAccount(account);

            // 检查账号是否存在
            boolean b = loginuserService.checkAccount(loginuser);
            if (!b){
                break;
            }
        }
        // 密码
        String password = namePy + "8520";
        loginuser.setPassword(password);
        // 获取浙政钉id
        String accountId = data.get("accountId").getAsString();
        loginuser.setAccountId(accountId);

        // 检查账号是否存在
        boolean b = loginuserService.checkAccount(loginuser);
        if (b){
            return Result.fail("账号已经存在");
        }

        return Result.success(loginuser);
    }

}
