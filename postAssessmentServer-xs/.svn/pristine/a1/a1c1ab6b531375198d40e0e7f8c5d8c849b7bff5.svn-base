package com.beneway.common.common.utils.log;

import java.lang.annotation.*;

/**
 * @Author zxc
 * Date on 2020/3/5  13:51
 */

@Target(ElementType.METHOD) //注解放置目标位置  method:方法级
@Retention(RetentionPolicy.RUNTIME) //注解在什么阶段执行
@Documented // 生成文档
public @interface MyLog {
    String operation() default ""; //操作名称
}
