package com.example.rabbitmq.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class TestConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 消息消费监听.
     * @param message 消息体
     * @param channel 渠道
     */
    @RabbitListener(queues = {"${rabbitmq.test.queueName}"}, containerFactory = "rabbitListenerContainerFactory") // 从配置中获取队列名称
    public void receiveMessage(Message message, Channel channel) {
        // deliveryTag:这是每条消息的唯一标识符，用于标记消息的交付状态。
        // RabbitMQ 为每条消息分配一个 delivery tag，它在消息传递时被使用
        // multiple:这是一个布尔值，指示是否要确认批量消息。
        // 在这里，false 表示只确认当前消息。如果设置为 true，则会确认所有小于或等于当前 delivery tag 的消息。
        try {
            String json = new String(message.getBody(), StandardCharsets.UTF_8);
            Object o = objectMapper.readValue(json, Object.class);
            log.info("json；【{}】", objectMapper.writeValueAsString(o));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            //处理异常，选择不确认，消息将被重新投递
            throw new RuntimeException(e);
        }
    }
}
