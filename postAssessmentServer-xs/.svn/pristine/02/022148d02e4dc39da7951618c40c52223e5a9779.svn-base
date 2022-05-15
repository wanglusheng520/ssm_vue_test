package com.beneway.common.common.rabbitMQ.provider;

import com.beneway.common.common.utils.SpringContextHolder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Map;

/**
 * Description: rabbit生产公用方法
 *
 * @author zxc
 * @date 2022/1/19 17:38
 */
public class ProviderUtils {

    //先采用手动注入的形式
    private final RabbitTemplate rabbitTemplate = SpringContextHolder.getBean(RabbitTemplate.class);

    public void providerAddRabbit(String exChangeName, String routingKey, Map<String,Object> map){
        rabbitTemplate.convertAndSend(exChangeName, routingKey, map);
    }

    public static void providerAdd(String exChangeName, String routingKey, Map<String,Object> map){
        ProviderUtils p = new ProviderUtils();
        p.providerAddRabbit(exChangeName, routingKey, map);
    }

}
