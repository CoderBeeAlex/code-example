package com.example.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 应用启动类.
 */
@Slf4j
@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        log.info("启动rabbitmq-消费者端...");
        SpringApplication.run(ConsumerApplication.class);
    }
}
