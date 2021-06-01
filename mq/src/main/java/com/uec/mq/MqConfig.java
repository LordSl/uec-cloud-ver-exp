package com.uec.mq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MqConfig {
    private final String hostName = "localhost:9876";
    private final String groupName = "group-xgs-uec";

    public DefaultMQProducer getDefaultPro() {
        // 实例化消息生产者Producer
        DefaultMQProducer defaultPro = new DefaultMQProducer(groupName);
        // 设置NameServer的地址
        defaultPro.setNamesrvAddr(hostName);
        return defaultPro;
    }

    public DefaultMQProducer getAsyncPro() {
        // 实例化消息生产者Producer
        DefaultMQProducer asyncPro = new DefaultMQProducer(groupName);
        // 设置NameServer的地址
        asyncPro.setNamesrvAddr(hostName);
        asyncPro.setRetryTimesWhenSendAsyncFailed(0);
        return asyncPro;
    }

    public DefaultMQProducer getOneWayPro() {
        // 实例化消息生产者Producer
        DefaultMQProducer oneWayPro = new DefaultMQProducer(groupName);
        // 设置NameServer的地址
        oneWayPro.setNamesrvAddr(hostName);
        return oneWayPro;
    }

    @Bean
    public DefaultMQPushConsumer getDefaultCon() throws MQClientException {
        // 实例化消费者
        DefaultMQPushConsumer defaultCon = new DefaultMQPushConsumer(groupName);
        // 设置NameServer的地址
        defaultCon.setNamesrvAddr(hostName);
        // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
        defaultCon.subscribe("TopicTest", "*");
        // 注册回调实现类来处理从broker拉取回来的消息
        defaultCon.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                // 标记该消息已经被成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        defaultCon.start();
        System.out.printf("Consumer Started.%n");
        return defaultCon;
    }
}
