package com.beneway.common.common.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @email 2434017367@qq.com
 * @author: zhy
 * @date: 2021/9/26
 * @time: 15:26
 */
@Configuration
public class BaseConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
        return restTemplateBuilder.build();
    }

}
