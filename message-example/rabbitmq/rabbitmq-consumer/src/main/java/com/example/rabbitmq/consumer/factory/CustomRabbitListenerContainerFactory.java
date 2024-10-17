package com.example.rabbitmq.consumer.factory;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpoint;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

public class CustomRabbitListenerContainerFactory extends SimpleRabbitListenerContainerFactory {

    @Override
    protected void initializeContainer(SimpleMessageListenerContainer instance, RabbitListenerEndpoint endpoint) {
        super.initializeContainer(instance, endpoint);
        //进行声明，实现自动创建队列交换机和绑定
        instance.setAutoDeclare(true);
        instance.setMissingQueuesFatal(true);
    }
}
