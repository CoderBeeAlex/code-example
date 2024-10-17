package com.example.rabbitmq.consumer.config.properties;

import lombok.Data;

@Data
public class RabbitMqSubProperties {

    private String queueName;

    private String exchangeName;

    private String routingKey;
}
