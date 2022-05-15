package com.beneway.common.common.rabbitMQ.consumer;

import com.beneway.common.common.rabbitMQ.provider.RabbitMQConstant;
import com.beneway.common.entity.message.Message;
import com.beneway.common.service.message.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class ConsumerReceiverMess {


    @Autowired
    private MessageService messageService;

    //@RabbitListener(queues = RabbitMQConstant.QUEUE_TOPIC_MESS)//监听的队列名称
    @RabbitHandler
    public void processMess(Map<String,Object> message) {

        String agencyIds = message.get("agencyIds").toString();
        String stage = message.get("stage").toString();
        String assId = message.get("assId").toString();
        String assType = message.get("assType").toString();

        List<String> agencyIdList = Arrays.asList(agencyIds.split(","));
        List<Message> messages = new ArrayList<>();
        for (String s : agencyIdList) {
            Message mess = Message.builder()
                    .assId(assId)
                    .assType(assType)
                    .matter(stage)
                    .createTime(new Date())
                    .unitId(Integer.valueOf(s))
                    .build();
            messages.add(mess);
        }
        messageService.saveBatch(messages);
    }

}
