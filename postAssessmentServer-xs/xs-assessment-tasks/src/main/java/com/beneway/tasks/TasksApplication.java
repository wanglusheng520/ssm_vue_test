package com.beneway.tasks;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @email 2434017367@qq.com
 * @author: zhy
 * @date: 2021/9/17
 * @time: 20:45
 */
@EnableScheduling    //开启调度任务
@MapperScan(value = {"com.beneway.admin.dao.**", "com.beneway.common.dao.**","com.beneway.**.dao"})
@SpringBootApplication(scanBasePackages = {"com.beneway.*"})
public class TasksApplication {

    public static void main(String[] args) {
        SpringApplication.run(TasksApplication.class, args);
    }

}
