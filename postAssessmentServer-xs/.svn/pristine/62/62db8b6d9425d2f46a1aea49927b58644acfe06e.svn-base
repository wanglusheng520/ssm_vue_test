package com.beneway.common.common.rabbitMQ.consumer;

import com.beneway.common.common.rabbitMQ.provider.RabbitMQConstant;
import com.beneway.common.common.utils.ClassUtil;
import com.beneway.common.entity.syslog.SysLog;
import com.beneway.common.service.syslog.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Description: 消费
 *
 * @author zxc
 * @date 2022/1/25 15:29
 */
@Component
@Slf4j
public class ConsumerReceiver {

    @Autowired
    SysLogService sysLogService;
    @Autowired
    ConsumerBean consumerBean;


    //@RabbitListener(queues = RabbitMQConstant.QUEUE_TOPIC_ZZD)//监听的队列名称
    @RabbitHandler
    public void processZZD(Map<String,Object> message) {
        log.error("浙政钉卡片消息  : " + message.toString());
    }

    //@RabbitListener(queues = RabbitMQConstant.QUEUE_TOPIC_LOG)//监听的队列名称
    @RabbitHandler
    public void processLog(Map<String,Object> message) {
        SysLog sysLog = ClassUtil.toClass(message,SysLog.class);
        if(consumerBean.getLog()){
            sysLogService.save(sysLog);
        }
    }
}
