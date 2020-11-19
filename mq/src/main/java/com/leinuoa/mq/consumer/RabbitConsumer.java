package com.leinuoa.mq.consumer;

import com.leinuoa.mq.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitConsumer {
    @RabbitListener(queues = RabbitConfig.QUEUE_INFORM_EMAIL)
    public void receiveMessage(String message){
        System.out.println("接收到的消息======>> "+message);
    }
}
