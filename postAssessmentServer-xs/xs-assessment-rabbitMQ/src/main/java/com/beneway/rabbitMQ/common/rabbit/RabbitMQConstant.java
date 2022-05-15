package com.beneway.rabbitMQ.common.rabbit;


public class RabbitMQConstant {
    // direct交换机
    public static final String EXCHANGE_DIRECT = "direct_exchange_hpg";
    // work交换机
    public static final String EXCHANGE_WORK = "work_exchange_hpg";
    // topic交换机
    public static final String EXCHANGE_TOPIC = "topic_exchange_hpg";
    // fanout交换机
    public static final String EXCHANGE_FANOUT = "fanout_exchange_hpg";

    //direct模式下的两个队列，代表两种消息模式
    public static final String SEND_MESSAGE = "direct_SEND_MESSAGE_HPG";
    public static final String DING = "direct_DING_HPG";
    //direct模式下的两个路由，用于绑定到队列
    public static final String MESSAGE_KEY = "MESSAGE_KEY_HPG";
    public static final String DING_KEY = "DING_KEY_HPG";
}
