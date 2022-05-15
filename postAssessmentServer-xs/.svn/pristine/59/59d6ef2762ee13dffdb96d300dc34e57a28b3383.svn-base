package com.beneway.common.common.utils.log;

import java.lang.annotation.*;

/**
 * Description: 用于更新内容字段关联其他表
 *
 * @author zxc
 * @date 2021/5/24 9:45
 */
@Retention(RetentionPolicy.RUNTIME) // 注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.FIELD})//定义注解的作用目标**作用范围字段、枚举的常量/方法
@Documented                 //说明该注解将被包含在javadoc中
public @interface Dict {
    int neKey() default 0;
    String dicCode() default "";
    boolean isCommon() default false;
}
