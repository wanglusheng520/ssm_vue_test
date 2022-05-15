package com.beneway.tasks.common.config;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @email 2434017367@qq.com
 * @author: zhy
 * @date: 2022/1/5
 * @time: 9:36
 */
@Log4j2
@Aspect
@Component
public class JobLogAspect {

    @Pointcut("execution(public void com.beneway.tasks.common.job..*.execute())")
    public void point() {}

    /**
     * 环绕通知,环绕增强，相当于MethodInterceptor
     * @param pjp
     * @return
     */
    @Around("point()")
    public Object arround(ProceedingJoinPoint pjp) {
        Object target = pjp.getTarget();
        log.info(target.toString() + " 开始执行");
        // 运行方法
        try {
            Object proceed = pjp.proceed();
            log.info(target.toString() + " 执行成功");
        } catch (Throwable throwable) {
            log.error(target.toString() + " 执行失败");
            throwable.printStackTrace();
        }
        return null;
    }

}
