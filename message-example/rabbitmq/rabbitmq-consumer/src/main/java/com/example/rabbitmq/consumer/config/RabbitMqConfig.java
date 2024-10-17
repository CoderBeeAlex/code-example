package com.example.rabbitmq.consumer.config;

import com.example.rabbitmq.core.config.properties.RabbitMqProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class RabbitMqConfig {

    @Resource
    private RabbitMqProperties properties;

    /**
     * 队列1.
     * @return Queue
     */
    @Bean
    public Queue testQueue() {
        //durable指定队列是否持久化。持久化队列在 RabbitMQ 服务器重启后依然存在
        //exclusive 指定队列是否为独占模式。独占队列只能由创建它的连接使用，连接断开后队列会被自动删除
        //autoDelete 指定队列在最后一个消费者断开连接后是否自动删除。
        //arguments (Map<String, Object>):为队列指定额外的参数，如 TTL（存活时间）、死信交换机等
        return new Queue(properties.getTest().getQueueName(),
                true,
                false,
                false);
    }

    /**
     * 直连交换机.
     * @return DirectExchange.
     */
    @Bean
    public DirectExchange testExchange() {
        // durable指定交换机是否持久化。持久化的交换机在 RabbitMQ 服务器重启后依然存在。
        // autoDelete 指定交换机在没有绑定的队列时是否自动删除。
        return new DirectExchange(properties.getTest().getExchangeName());
    }

    /**
     * 队列和交换机绑定.
     * @param testQueue 队列
     * @param testExchange 交换机.
     * @return Binding
     */
    @Bean
    public Binding binding(Queue testQueue, DirectExchange testExchange) {
        // 路由键（Routing Key）用于将消息路由到相应的队列，具体用法和匹配方式取决于交换机的类型
        // 1.直接交换机（Direct Exchange）
        // 匹配方式: 精确匹配
        // 说明: 直接交换机根据消息的路由键与绑定到交换机的队列的路由键进行一对一的比较。
        // 只有当路由键完全匹配时，消息才会被路由到相应的队列。
        // 2.主题交换机（Topic Exchange）
        // 匹配方式: 模糊匹配
        // 示例: 如果绑定的路由键是 stock.#，
        // 那么任何以 stock. 开头的路由键（如 stock.us 或 stock.us.nyse）都会匹配。
        // 匹配规则：
        // * 匹配一个单词；# 匹配零个或多个单词.
        // 3.扇形交换机（Fanout Exchange）
        // 匹配方式: 不使用路由键
        // 扇形交换机将消息广播到所有绑定的队列，而不考虑路由键。
        return BindingBuilder
                .bind(testQueue)
                .to(testExchange)
                .with(properties.getTest().getRoutingKey());
    }

    /**
     * 队列2.
     * @return Queue.
     */
    @Bean
    public Queue test2Queue() {
        return new Queue(properties.getTest2().getQueueName(),
                true,
                false,
                false);
    }

    /**
     * 主题交换机.
     * @return TopicExchange
     */
    @Bean
    public TopicExchange test2Exchange() {
        return new TopicExchange(properties.getTest2().getExchangeName(),
                true,
                false);
    }

    @Bean
    public Binding test2Bind() {
        return BindingBuilder.bind(test2Queue()).to(test2Exchange()).with(properties.getTest2().getRoutingKey());
    }

    @Bean
    public Queue test3queue() {
        return new Queue(properties.getTest2().getQueueName(),
                true, false, false);
    }

    // 创建扇形交换机的 Bean
    @Bean
    public FanoutExchange test3FanoutExchange() {
        return new FanoutExchange(properties.getTest3().getQueueName(), true, false);
    }

    // 绑定队列到扇形交换机
    @Bean
    public Binding test3Bind(FanoutExchange test3FanoutExchange, Queue test3queue) {
        return BindingBuilder.bind(test3queue).to(test3FanoutExchange);
    }

    // 多个队列绑定同一个交换机,使用相同的ruoting—key或者不同的ruotig-key进行绑定
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("test-v1-exchange", true, false);
    }

    @Bean
    public Queue queue1() {
        return new Queue("test-queue1", true, false, false);
    }

    @Bean
    public Queue queue2() {
        return new Queue("test-queue2", true, false, false);
    }

    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(queue1()).to(exchange()).with("common.routing.key");
    }

    @Bean
    public Binding binding2() {
        return BindingBuilder.bind(queue2()).to(exchange()).with("common.routing.key");
    }

    //一个队列可以绑定到多个交换机

    @Bean
    public Queue myQueue() {
        return new Queue("myQueue", true, false, false);
    }

    @Bean
    public DirectExchange exchange1() {
        return new DirectExchange("exchange1", true, false);
    }

    @Bean
    public DirectExchange exchange2() {
        return new DirectExchange("exchange2", true, false);
    }

    @Bean
    public Binding binding01() {
        return BindingBuilder.bind(myQueue()).to(exchange1()).with("routing.key1");
    }

    @Bean
    public Binding binding02() {
        return BindingBuilder.bind(myQueue()).to(exchange2()).with("routing.key2");
    }
}
