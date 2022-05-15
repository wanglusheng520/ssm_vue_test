package com.beneway.common.common.utils.dd;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.xxpt.gateway.shared.client.http.ExecutableClient;
import com.alibaba.xxpt.gateway.shared.client.http.GetClient;
import com.alibaba.xxpt.gateway.shared.client.http.PostClient;
import com.beneway.common.common.config.AppConfig;
import com.beneway.common.common.utils.dd.entity.msg.Card;
import com.beneway.common.dao.token.TokenDao;
import com.beneway.common.entity.system.login.Loginuser;
import com.beneway.common.service.system.login.LoginuserService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.taobao.api.internal.util.json.JSONWriter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @email 2434017367@qq.com
 * @author: zhy
 * @date: 2020/8/28
 * @time: 10:19
 */
@Log4j2
@Lazy
@Component
public class ZWDDUtils {

    @Value("${dd-config.pc.key}")
    private String key;

    @Value("${dd-config.pc.secret}")
    private String secret;

    @Value("${dd-config.domainname}")
    private String domainname;

    @Value("${dd-config.pc.appId}")
    private String appId;

    @Value("${dd-config.pc.tenantId}")
    private String tenantId;

    // 发送消息api地址
    private static final String MESSAGE_API = "/message/workNotification";

    /**
     * 获取之前都要重新设置防止被替换
     * @return
     */
    private ExecutableClient getExecutableClient(){
        ExecutableClient executableClient = ExecutableClient.getInstance();
        executableClient.setAccessKey(key);
        executableClient.setSecretKey(secret);
        executableClient.setDomainName(domainname);
        executableClient.setProtocal("https");
        executableClient.init();
        return executableClient;
    }

    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private LoginuserService loginuserService;

    private static final String TOKEN_TYPE = "T";

    private String getToken(){
        return tokenDao.getToken(TOKEN_TYPE);
    }

    public void flushToken(){
        try {
            GetClient client = getExecutableClient().newGetClient("/gettoken.json");
            client.addParameter("appkey", key);
            client.addParameter("appsecret", secret);
            String s = client.get();

            boolean success = ZWDDBaseUtils.getJsonSuccess(s);
            if (success){
                JsonObject data = ZWDDBaseUtils.getJsonData(s);
                String token = data.get("accessToken").getAsString();
                log.info("获取政务钉钉token：" + token);
                String myToken = getToken();
                if (null == myToken){
                    tokenDao.insert(token, TOKEN_TYPE);
                }else if (!myToken.equals(token)){
                    tokenDao.update(token, TOKEN_TYPE);
                }
            }else{
                log.error("获取政务钉钉token失败 返回数据：" + s);
            }
        } catch (JsonSyntaxException e) {
            log.error("获取政务钉钉token数据解析失败", e);
        } catch (Exception e) {
            log.error("获取政务钉钉token数据请求失败", e);
        }
    }

    public JsonObject getUserInfo(String auth_code){
        try {
            PostClient client = getExecutableClient().newPostClient("/rpc/oauth2/dingtalk_app_user.json");
            client.addParameter("access_token", getToken());
            client.addParameter("auth_code", auth_code);
            String s = client.post();
            boolean success = ZWDDBaseUtils.getJsonSuccess(s);
            if (success){
                log.info("获取政务钉钉用户详情成功 返回数据：" + s);
                return ZWDDBaseUtils.getJsonData(s);
            }else{
                log.error("获取政务钉钉用户详情失败 返回数据：" + s);
            }
        }  catch (JsonSyntaxException e) {
            log.error("获取政务钉钉用户详情数据解析失败", e);
        } catch (Exception e) {
            log.error("获取政务钉钉用户详情数据请求失败", e);
        }
        return null;
    }

    /**
     *  发送卡片消息
     * @return
     */
    public boolean sendCardMsg(String userId, Card card){
        log.info("============== 发送工作通知开始 ==============");

        PostClient postClient = getExecutableClient().newPostClient(MESSAGE_API);

        // 获取用户信息
        Loginuser loginuser = loginuserService.getById(userId);
        postClient.addParameter("tenantId", tenantId);
        postClient.addParameter("receiverIds", loginuser.getAccountId());

        Map<String, Object> param = new HashMap<>();
        param.put("msgtype", "action_card");
        param.put("action_card", card);
        String msg = (new JSONWriter(false, false, true)).write(param);

        log.info("接受人：" + loginuser.getUsername());
        log.info("消息内容：" + msg);

        postClient.addParameter("msg", msg);
        String apiResult = postClient.post();
        log.info(apiResult);
        boolean success = JSONObject.parseObject(apiResult).getBoolean("success");
        if (success){
            log.info("发送成功");
        }else{
            log.error(apiResult);
        }

        log.info("============== 发送工作通知结束 ==============");
        return success;
    }

}
