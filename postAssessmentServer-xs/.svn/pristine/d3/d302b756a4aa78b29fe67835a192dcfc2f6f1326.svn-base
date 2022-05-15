package com.beneway.common.common.rabbitMQ.provider;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {
    /**
     * 浙政钉
     */
    //1.声明注册direct模式交换机
    @Bean
    public TopicExchange topicExchangeZzd(){
        return new TopicExchange(RabbitMQConstant.EXCHANGE_TOPIC_ZZD,true, false);
    }

    //2.声明队列
    @Bean
    public Queue topicQueueZzd() {
        return new Queue(RabbitMQConstant.QUEUE_TOPIC_ZZD,true, false, false);
    }
    //3.完成绑定关系（队列和交换机完成绑定关系）
    @Bean
    public Binding bindingDirectZzd() {
        return BindingBuilder.bind(topicQueueZzd())
                .to(topicExchangeZzd())
                .with(RabbitMQConstant.ROUTING_KEY_TOPIC_ZZD);
    }

    /**
     * 日志
     */
    //1.声明注册topic模式交换机
    @Bean
    public TopicExchange topicExchangeLog(){
        return new TopicExchange(RabbitMQConstant.EXCHANGE_TOPIC_LOG,true, false);
    }
    //2.声明队列
    @Bean
    public Queue topicQueueLog() {
        return new Queue(RabbitMQConstant.QUEUE_TOPIC_LOG,true, false, false);
    }
    //3.完成绑定关系（队列和交换机完成绑定关系）
    @Bean
    public Binding bindingDirectLog() {
        return BindingBuilder.bind(topicQueueLog())
                .to(topicExchangeLog())
                .with(RabbitMQConstant.ROUTING_KEY_TOPIC_LOG);
    }


    /**
     * 填报计算
     */
    //1.声明注册topic模式交换机
    @Bean
    public TopicExchange topicExchangeTb(){
        return new TopicExchange(RabbitMQConstant.EXCHANGE_TOPIC_TB,true, false);
    }
    //2.声明队列
    @Bean
    public Queue topicQueueTb() {
        return new Queue(RabbitMQConstant.QUEUE_TOPIC_TB,true, false, false);
    }
    //3.完成绑定关系（队列和交换机完成绑定关系）
    @Bean
    public Binding bindingDirectTb() {
        return BindingBuilder.bind(topicQueueTb())
                .to(topicExchangeTb())
                .with(RabbitMQConstant.ROUTING_KEY_TOPIC_TB);
    }

    /**
     * 预警
     */
    @Bean
    public Queue queueTopicYj() {
        return new Queue(RabbitMQConstant.QUEUE_TOPIC_YJ,true, false, false);
    }
    @Bean
    public Binding routing_key_topic_yj() {
        return BindingBuilder.bind(queueTopicYj())
                .to(topicExchangeTb())
                .with(RabbitMQConstant.ROUTING_KEY_TOPIC_YJ);
    }


    /**
     * 信息
     */
    @Bean
    public Queue queueTopicMess() {
        return new Queue(RabbitMQConstant.QUEUE_TOPIC_MESS,true, false, false);
    }
    @Bean
    public Binding routing_key_topic_mess() {
        return BindingBuilder.bind(queueTopicMess())
                .to(topicExchangeTb())
                .with(RabbitMQConstant.ROUTING_KEY_TOPIC_MESS);
    }

}
