package com.example.rabbitmq.core.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "rabbitmq")
public class RabbitMqProperties {

    private RabbitMqSubProperties test;

    private RabbitMqSubProperties test2;

    private RabbitMqSubProperties test3;
}
