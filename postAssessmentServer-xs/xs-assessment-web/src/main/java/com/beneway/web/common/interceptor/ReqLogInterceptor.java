package com.beneway.web.common.interceptor;

import com.beneway.common.common.rabbitMQ.provider.ProviderUtils;
import com.beneway.common.common.rabbitMQ.provider.RabbitMQConstant;
import com.beneway.web.common.utils.IPUtil;
import com.beneway.common.common.utils.jwt.LoginUserUtils;
import com.beneway.common.common.utils.log.MyLog;
import com.beneway.common.entity.system.login.Loginuser;
import com.beneway.common.system.entity.sys_user.vo.LoginUserInfo;
import com.beneway.web.common.exception.MyExceptionInfo;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @email 2434017367@qq.com
 * @author: zhy
 * @date: 2021/1/29
 * @time: 9:59
 */
@Log4j2
public class ReqLogInterceptor implements HandlerInterceptor {

    private static final Gson gson = new Gson();

    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<Long>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 保存请求开始时的时间毫秒值
        long timeMillis = System.currentTimeMillis();
        threadLocal.set(timeMillis);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        if ("OPTIONS".equals(request.getMethod())){
            return;
        }

        try {
            long startTime = threadLocal.get();
            threadLocal.remove();

            long endTime = System.currentTimeMillis();
            // 获取请求url
            String requestURI = request.getRequestURI();
            // 获取请求方法
            String method = request.getMethod();
            // 获取请求ip
            String ip = IPUtil.getIpAddr(request);
            // 获取请求用户
            LoginUserInfo loginUser = LoginUserUtils.getLoginUserInfo();
            String username = null;
            if (loginUser != null){
                username = loginUser.getUsername();
            }
            // 获取请求参数
            Map<String, String[]> parameterMap = request.getParameterMap();
            String reqPar = gson.toJson(parameterMap);
            // 计算请求执行耗时
            long duration = endTime - startTime;

            // 日志封装
            String logs = String.format("用户：%s，uri：%s，method：%s，ip：%s，执行耗时：%dms，请求参数：%s", username, requestURI, method, ip, duration, reqPar);

            // 查看是否有异常信息
            MyExceptionInfo myExceptionInfo = MyExceptionInfo.getExceptionInfo();
            if (myExceptionInfo != null){
                logs = "errCode：" + myExceptionInfo.getCode() + "，errMsg：" + myExceptionInfo.getMsg() + "，" + logs;
                log.error(logs, myExceptionInfo.getE());
            }else{
                log.info(logs);
            }

            String operationName = "";
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method methods = handlerMethod.getMethod();
            //检查是否有MyLog注释，有则跳过认证
            if (methods.isAnnotationPresent(MyLog.class)) {
                MyLog myLog = methods.getAnnotation(MyLog.class);
                operationName = myLog.operation();
            }
            //将记录保存到数据库
            addMap(requestURI,method,ip,duration,reqPar,operationName);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addMap(String requestURI,String method,String ip,Long duration,String reqPar,String operationName){
        Map<String,Object> map = new HashMap<>();
        map.put("requestUrl",requestURI);
        map.put("method",method);
        map.put("ip",ip);
        map.put("duration",duration);
        map.put("reqPar",reqPar);
        map.put("time",new Date());
        LoginUserInfo loginUser = LoginUserUtils.getLoginUserInfo();
        if(null != loginUser){
            map.put("userId",loginUser.getId());
            map.put("userName",loginUser.getUsername());
        }
        map.put("operationName",operationName);
        ProviderUtils.providerAdd(RabbitMQConstant.EXCHANGE_TOPIC_LOG, RabbitMQConstant.ROUTING_KEY_TOPIC_LOG, map);
    }

}
