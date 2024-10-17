# 消息中间件样例

## rabbitmq-consumer【消费者】
> 1. 实现消费者的队列、交换机、绑定的自动创建
> 2. 消息数据的消费-手动确认/自动确认
> 3. 自定义容器（必须实现，才能自动创建队列等）
> 4. 待实现如何动态化配置，自动创建队列等（#TODO)

## rabbitmq-provider【生产者】
> 1. 实现生产端json数据发送

## rabbitmq的配置信息
```yaml
spring:
  rabbitmq:
    # 地址
    host: localhost
    # 端口号
    port: 5672
    # 账号
    username: guest
    # 密码
    password: guest
    listener:
      direct:
        # 消费者的消息确认模式，设定为手动确认（manual），自动确认（auto）,none
        acknowledge-mode: manual
    template:
      # 配置发送消息时是否强制路由。
      #	•	当 mandatory 设置为 true 时，如果消息无法路由到任何队列（即没有绑定符合的队列），RabbitMQ 会将消息返回给生产者（通过 ReturnCallback）。
      #	•	如果 mandatory 为 false，则消息丢失（默认行为是将消息丢弃）。
      #	•	强制路由适用于确保消息不丢失的场景，尤其是在要求严格消息投递保障的业务中。
      mandatory: true
    # 设置连接 RabbitMQ 服务器的超时时间，单位为毫秒。
    connection-timeout: 15000
    #  设置心跳间隔时间，单位为秒。
    requested-heartbeat: 60

```