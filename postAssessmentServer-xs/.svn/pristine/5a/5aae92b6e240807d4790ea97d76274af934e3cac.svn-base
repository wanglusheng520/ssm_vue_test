package com.beneway.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @email 2434017367@qq.com
 * @author: zhy
 * @date: 2021/9/17
 * @time: 15:43
 */
@EnableCaching
@EnableAspectJAutoProxy(proxyTargetClass = true) // aop切面
@MapperScan(value = {"com.beneway.admin.dao.**", "com.beneway.common.dao.**","com.beneway.**.dao"})
@SpringBootApplication(scanBasePackages = {"com.beneway.*"})
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
