package com.beneway.common.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @email 2434017367@qq.com
 * @author: zhy
 * @date: 2021/9/6
 * @time: 13:42
 */
@Data
@Component
public class AppConfig {

    @Value("${spring.profiles.active}")
    private String active;

    public boolean isActiveProd(){
        return "prod".equals(active);
    }

}
