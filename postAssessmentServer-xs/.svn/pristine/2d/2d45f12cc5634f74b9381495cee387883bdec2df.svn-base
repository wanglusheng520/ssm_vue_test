package com.beneway.tasks.common.config;

/**
 * @email 2434017367@qq.com
 * @author: zhy
 * @date: 2022/1/4
 * @time: 18:26
 */
public class TaskEntity {

    private Class<? extends ScheduledOfTask> cls;

    private String cron;

    private String explain;

    public TaskEntity(Class<? extends ScheduledOfTask> cls, String cron, String explain) {
        this.cls = cls;
        this.cron = cron;
        this.explain = explain;
    }

    public Class<? extends ScheduledOfTask> getCls() {
        return cls;
    }

    public String getCron() {
        return cron;
    }

    public String getExplain() {
        return explain;
    }

    @Override
    public String toString() {
        return "class:" + cls.getName() + ", cron:" + cron + ", explain:" + explain;
    }

}
