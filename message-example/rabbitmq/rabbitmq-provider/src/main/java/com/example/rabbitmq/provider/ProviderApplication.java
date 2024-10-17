package com.example.rabbitmq.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@ComponentScan(basePackages = {"com.example.rabbitmq"}) // 添加子模块的包名
@SpringBootApplication
public class ProviderApplication {

    public static void main(String[] args) {
        log.info("启动rabbitmq-提供者端...");
        SpringApplication.run(ProviderApplication.class);
    }
}
