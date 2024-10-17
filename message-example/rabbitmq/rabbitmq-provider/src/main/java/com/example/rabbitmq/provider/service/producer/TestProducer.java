package com.example.rabbitmq.provider.service.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Service
public class TestProducer {

    @Resource
    private AmqpTemplate amqpTemplate;

    @Resource
    private ObjectMapper objectMapper; // 用于序列化对象

    /**
     * 发送消息.
     *
     * @param exchangeName 队列名称
     * @param message   要发送的消息对象
     */
    public void sendMessage(String exchangeName, String ruotingKey, Object message) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(message); // 将对象序列化为 JSON
            log.info("当前时间：{}, 发送一条信息: {}", new Date(), jsonMessage);

            // 构建消息属性，设置内容类型为 JSON
            MessageProperties messageProperties = MessagePropertiesBuilder
                    .newInstance()
                    .setContentType("application/json")  // 设置消息内容为 JSON
                    .build();

            // 创建消息
            Message msg = new Message(jsonMessage.getBytes(), messageProperties);
            amqpTemplate.convertAndSend(exchangeName, ruotingKey, msg);
        } catch (JsonProcessingException e) {
            log.error("消息序列化失败: {}", e.getMessage(), e);
        }
    }
}