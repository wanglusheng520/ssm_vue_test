package com.beneway.web.common.aspect;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beneway.common.entity.operationlog.OperationLog;
import com.beneway.common.service.operationlog.OperationLogService;
import com.beneway.web.common.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.Date;

@Slf4j
@Aspect
@Component
public class OperationLogAspect {

    @Autowired
    private OperationLogService operationLogService;


    @Pointcut("@annotation(com.beneway.web.common.annotation.Log)")
    public void log(){

    }

    //返回后不抛出异常就增强
    @AfterReturning(value = "log()" , returning = "result")
    public void doBefore(JoinPoint joinPoint , Object result) throws ClassNotFoundException, NoSuchMethodException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取方法
        Method m = signature.getMethod();
        //获取方法上的注解
        Log annotation = m.getAnnotation(Log.class);
        //获取注解中的状态
        String stage = annotation.stage().getStage();
        //获取参数
        Object[] args = joinPoint.getArgs();
        String s = JSON.toJSONString(args[0]);
        JSONObject jsonObject = JSONObject.parseObject(s);
        String assId = (String) jsonObject.get("assId");
        String assType = (String) jsonObject.get("assType");
        if(StringUtils.isNotBlank(assId) && StringUtils.isNotBlank(assType)){
            OperationLog operationLog = OperationLog.builder().assId(assId).stage(stage).assType(assType).createTime(new Date()).build();
            operationLogService.save(operationLog);
        }
    }

}
