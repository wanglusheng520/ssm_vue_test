package com.beneway.rabbitMQ.common.rabbit.utils;

import com.beneway.rabbitMQ.common.rabbit.RabbitMQConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfigDirect {

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(RabbitMQConstant.EXCHANGE_DIRECT , true , false);
    }

    @Bean
    public Queue directCardQueue(){
        return new Queue(RabbitMQConstant.SEND_MESSAGE , true);
    }

    @Bean
    public Queue directDingQueue(){
        return new Queue(RabbitMQConstant.DING , true);
    }

    @Bean
    public Binding directCardBing(){
        return BindingBuilder.bind(directCardQueue()).to(directExchange()).with(RabbitMQConstant.MESSAGE_KEY);
    }

    @Bean
    public Binding directDingBing(){
        return BindingBuilder.bind(directDingQueue()).to(directExchange()).with(RabbitMQConstant.DING_KEY);
    }

}
