package com.beneway.common.common.utils.dd;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.xxpt.gateway.shared.client.http.ExecutableClient;
import com.alibaba.xxpt.gateway.shared.client.http.GetClient;
import com.alibaba.xxpt.gateway.shared.client.http.PostClient;
import com.beneway.common.common.utils.dd.entity.msg.Card;
import com.beneway.common.dao.token.TokenDao;
import com.beneway.common.entity.system.login.Loginuser;
import com.beneway.common.service.system.login.LoginuserService;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.taobao.api.internal.util.json.JSONWriter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Log4j2
@Lazy
@Component
public class ZWDDPhoneUtils {

    @Value("${dd-config.phone.key}")
    private String key;

    @Value("${dd-config.phone.secret}")
    private String secret;

    @Value("${dd-config.domainname}")
    private String domainname;

    @Value("${dd-config.phone.appId}")
    private String appId;

    @Value("${dd-config.phone.tenantId}")
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

    private static final String TOKEN_TYPE = "P";

    private String getToken(){
        String token = tokenDao.getToken(TOKEN_TYPE);
        log.info("获取token：" + token);
        return token;
    }

    public static void main(String[] args) {

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

    public JsonObject getUserInfoByMobile(String mobile){
        // 根据手机号获取 employeeCode和accountId
        JsonObject object = getByMobile(mobile);
        if (object != null){
            String employeeCode = object.get("employeeCode").getAsString();
            String accountId = object.get("accountId").getAsString();
            // 根据employeeCode获取员工信息
            JsonObject jsonObject = getEmployeeByCode(employeeCode);
            if (jsonObject != null){
                jsonObject.addProperty("accountId", accountId);
                return jsonObject;
            }
        }
        return null;
    }

    public JsonObject getByMobile(String mobile){
        try {
            PostClient client = getExecutableClient().newPostClient("/mozi/employee/get_by_mobile");
            client.addParameter("areaCode", "86");
            client.addParameter("tenantId", tenantId);
            client.addParameter("namespace", "local");
            client.addParameter("mobile", mobile);
            String s = client.post();
            boolean success = ZWDDBaseUtils.getJsonSuccess(s);
            if (success){
                log.info("获取政务钉钉数据成功 返回数据：" + s);
                return ZWDDBaseUtils.getJsonData(s);
            }else{
                log.error("获取政务钉钉数据失败 返回数据：" + s);
            }
        } catch (Exception e) {
           log.error("获取政务钉钉数据失败", e);
        }
        return null;
    }

    public JsonObject getEmployeeByCode(String employessCode){
        try {
            PostClient client = getExecutableClient().newPostClient("/mozi/employee/getEmployeeByCode");
            client.addParameter("employeeCode", employessCode);
            client.addParameter("tenantId", tenantId);
            String s = client.post();
            boolean success = ZWDDBaseUtils.getJsonSuccess(s);
            if (success){
                log.info("获取政务钉钉数据成功 返回数据：" + s);
                return ZWDDBaseUtils.getJsonData(s);
            }else{
                log.error("获取政务钉钉数据失败 返回数据：" + s);
            }
        } catch (Exception e) {
            log.error("获取政务钉钉数据失败", e);
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

    public String getSingleUrl(String pageUrl, Map<String, String> params) {
        String query = null;
        if (CollUtil.isNotEmpty(params)){
            Set<Map.Entry<String, String>> entries = params.entrySet();
            Iterator<Map.Entry<String, String>> iterator = entries.iterator();
            StringJoiner sj = new StringJoiner("&");
            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                String key = next.getKey();
                String value = next.getValue();
                if (StrUtil.isNotEmpty(key) && StrUtil.isNotEmpty(value)){
                    sj.add(key + "=" + value);
                }
            }
            query = sj.toString();
        }

        String singleUrl = "taurus://taurusclient/action/open_app?appId=" + appId + "&type=2";
        try {
            if (StrUtil.isNotEmpty(pageUrl)){
                pageUrl = URLEncoder.encode(pageUrl, "utf-8");
                singleUrl += ("&page=" + pageUrl);

                if (StrUtil.isNotEmpty(query)){
                    query = URLEncoder.encode(query, "utf-8");
                    singleUrl += ("&query=" + query);
                }
            }
        } catch (UnsupportedEncodingException e) {
            log.error("URLEncoder 编码失败", e);
        }

        return singleUrl;
    }


}
