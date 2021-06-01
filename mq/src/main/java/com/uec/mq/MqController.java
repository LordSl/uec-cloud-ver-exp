package com.uec.mq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.CountDownLatch2;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Api(
        value = "rocketmq的简单应用",
        tags = "mq"
)
@RestController
@RequestMapping("mq")
public class MqController {
    @Autowired
    MqConfig mqConfig;

    @ApiOperation("同步生产者")
    @PostMapping("/pro/default")
    public void defaultPro(@RequestBody String s) throws Exception {
        DefaultMQProducer producer = mqConfig.getDefaultPro();
        producer.start();

        // 创建消息，并指定Topic，Tag和消息体
        Message msg = new Message("TopicTest" /* Topic */,
                "TagA" /* Tag */,
                s.getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
        );
        // 发送消息到一个Broker
        SendResult sendResult = producer.send(msg);
        // 通过sendResult返回消息是否成功送达
        System.out.printf("%s%n", sendResult);

        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }

    @ApiOperation("异步生产者")
    @PostMapping("/pro/async")
    public void asyncPro(@RequestBody String s) throws Exception {
        DefaultMQProducer producer = mqConfig.getAsyncPro();
        producer.start();

        int messageCount = 1;
        // 根据消息数量实例化倒计时计算器
        final CountDownLatch2 countDownLatch = new CountDownLatch2(messageCount);

        final int index = 0;
        // 创建消息，并指定Topic，Tag和消息体
        Message msg = new Message("TopicTest",
                "TagA",
                "OrderID188",
                s.getBytes(RemotingHelper.DEFAULT_CHARSET));
        // SendCallback接收异步返回结果的回调
        producer.send(msg, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.printf("%-10d OK %s %n", index,
                        sendResult.getMsgId());
            }

            @Override
            public void onException(Throwable e) {
                System.out.printf("%-10d Exception %s %n", index, e);
                e.printStackTrace();
            }
        });

        // 等待5s
        countDownLatch.await(5, TimeUnit.SECONDS);
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }

    @ApiOperation("单向生产者")
    @PostMapping("/pro/oneway")
    public void onewayPro(@RequestBody String s) throws Exception {
        DefaultMQProducer producer = mqConfig.getOneWayPro();
        producer.start();

        // 创建消息，并指定Topic，Tag和消息体
        Message msg = new Message("TopicTest" /* Topic */,
                "TagA" /* Tag */,
                s.getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
        );
        // 发送单向消息，没有任何返回结果
        producer.sendOneway(msg);

        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }

}
