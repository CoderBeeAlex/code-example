package com.example.rabbitmq.provider;

import com.example.rabbitmq.core.config.properties.RabbitMqProperties;
import com.example.rabbitmq.core.dto.SearchDto;
import com.example.rabbitmq.provider.service.producer.TestProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
public class TestProducerTest {

    @Resource
    private TestProducer testProducer;

    @Resource
    private RabbitMqProperties properties;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Test
    public void sendStrMsgTest(){
        String  msg = "11111";
        testProducer.sendMessage(properties.getTest().getQueueName(), properties.getTest().getRoutingKey(), msg);
    }

    @Test
    public void sendObjTest() {
        final int SLEEP_TIME_MS = 3000; // 休眠时间，单位为毫秒
        while (true) {
            log.info("Preparing to send message...");

            SearchDto searchDto = new SearchDto();
            searchDto.setName("aaaaa");
            searchDto.setValue("bbbbb");

            String exchangeName = properties.getTest().getExchangeName();
            String routingKey = properties.getTest().getRoutingKey();
            String message;

            try {
                message = objectMapper.writeValueAsString(searchDto);
                log.info("Sending message to queue: {}, message: {}", exchangeName, message);
                testProducer.sendMessage(exchangeName, routingKey, message);
            } catch (JsonProcessingException e) {
                log.error("Error processing JSON: {}", e.getMessage(), e);
                // 处理异常或记录日志
            } catch (Exception e) {
                log.error("Failed to send message to queue: {}, error: {}", exchangeName, e.getMessage(), e);
            }

            try {
                Thread.sleep(SLEEP_TIME_MS); // 休眠1秒
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 恢复中断状态
                log.error("Thread was interrupted: {}", e.getMessage(), e);
                break; // 退出循环或处理逻辑
            }
        }
    }

}
