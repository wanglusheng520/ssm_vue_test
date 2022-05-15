package com.beneway.tasks.common.config;


import com.beneway.tasks.common.job.EnterLibraryJob;
import com.beneway.tasks.common.job.NormativeDocJob;
import com.beneway.tasks.common.job.PeriodWarnJob;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @email 2434017367@qq.com
 * @author: zhy
 * @date: 2022/1/4
 * @time: 17:48
 */
@Log4j2
@Configuration
public class MyScheduleConfig implements SchedulingConfigurer {

    @Autowired
    private ApplicationContext context;

    private static final Set<TaskEntity> taskList = new HashSet<>();

    static {
      //addTaskEntity(NormativeDocJob.class, "0 0 23 * * ?", "获取规范性文件");
      addTaskEntity(PeriodWarnJob.class, "0 0 23 * * ?", "填报周期预警");
      //addTaskEntity(EnterLibraryJob.class, "0 0 22 * * ?", "入库");
    }

    private static void addTaskEntity(Class cls, String cron, String explain){
        taskList.add(new TaskEntity(cls, cron, explain));
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        for (TaskEntity taskEntity : taskList) {
            Class<? extends ScheduledOfTask> cls = taskEntity.getCls();
            ScheduledOfTask task = context.getBean(cls);
            String cron = taskEntity.getCron();
            try {
                taskRegistrar.addTriggerTask(() -> {
                    task.execute();
                }, triggerContext -> new CronTrigger(cron).nextExecutionTime(triggerContext));
                log.info("添加定时任务成功：" + taskEntity);
            } catch (BeansException e) {
                log.info("添加定时任务失败：" + taskEntity);
                e.printStackTrace();
            }
        }
    }

}
