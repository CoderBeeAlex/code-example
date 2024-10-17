package com.example.rabbitmq.consumer.config;

import com.example.rabbitmq.consumer.factory.CustomRabbitListenerContainerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configurations {

    /**
     * 实例化创建消费者容器 SimpleRabbitListenerContainerFactory
     * 配置并发等相关信息.
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            ConnectionFactory connectionFactory
    ) {
        CustomRabbitListenerContainerFactory factory = new CustomRabbitListenerContainerFactory();
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        // 设置并发消费者数量
        factory.setConcurrentConsumers(6);
        // 设置最大并发消费者数量
        factory.setMaxConcurrentConsumers(30);
        // 预取计数
        factory.setPrefetchCount(3);
        // 消费者的消息确认模式，设定为手动确认（manual），自动确认（auto）,none
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        configurer.configure(factory, connectionFactory);
        return factory;
    }
}


