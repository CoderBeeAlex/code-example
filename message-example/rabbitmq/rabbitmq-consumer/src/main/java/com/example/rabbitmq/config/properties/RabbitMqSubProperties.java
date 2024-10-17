package com.example.rabbitmq.config.properties;

import lombok.Data;

@Data
public class RabbitMqSubProperties {

    private String queueName;

    private String exchangeName;

    private String routingKey;
}
