package com.beneway.common.common.utils.dd;

import com.alibaba.xxpt.gateway.shared.client.http.ExecutableClient;
import com.alibaba.xxpt.gateway.shared.client.http.GetClient;
import com.alibaba.xxpt.gateway.shared.client.http.PostClient;
import com.beneway.common.common.config.AppConfig;
import com.beneway.common.dao.token.TokenDao;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @email 2434017367@qq.com
 * @author: zhy
 * @date: 2021/8/18
 * @time: 16:49
 */
@Log4j2
@Lazy
@Component
public class ZWDDQrcodeUtils {

    @Value("${dd-config.qrcode.key}")
    private String key;

    @Value("${dd-config.qrcode.secret}")
    private String secret;

    @Value("${dd-config.domainname}")
    private String domainname;

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

    private static final String TOKEN_TYPE = "Q";

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
                log.info("获取政务钉钉扫码token：" + token);
                String myToken = getToken();
                if (null == myToken){
                    tokenDao.insert(token, TOKEN_TYPE);
                }else if (!myToken.equals(token)){
                    tokenDao.update(token, TOKEN_TYPE);
                }
            }else{
                log.error("获取政务钉钉扫码token失败 返回数据：" + s);
            }
        } catch (JsonSyntaxException e) {
            log.error("获取政务钉钉扫码token数据解析失败", e);
        } catch (Exception e) {
            log.error("获取政务钉钉扫码token数据请求失败", e);
        }
    }

    /**
     * 扫码获取临时code
     * @param code
     * @return
     */
    public JsonObject getUserInfoQrcode(String code){
        try {
            PostClient client = getExecutableClient().newPostClient("/rpc/oauth2/getuserinfo_bycode.json");
            client.addParameter("access_token", getToken());
            client.addParameter("code", code);
            String s = client.post();

            boolean success = ZWDDBaseUtils.getJsonSuccess(s);
            if (success){
                log.info("获取政务钉钉扫码用户详情成功 返回数据：" + s);
                return ZWDDBaseUtils.getJsonData(s);
            }else{
                log.error("获取政务钉钉扫码用户详情失败 返回数据：" + s);
            }
        }  catch (JsonSyntaxException e) {
            log.error("获取政务钉钉扫码用户详情数据解析失败", e);
        } catch (Exception e) {
            log.error("获取政务钉钉扫码用户详情数据请求失败", e);
        }
        return null;
    }

}
