package com.ynxu.spring.rocketmq.consumer;

import com.ynxu.spring.rocketmq.producer.MQProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Slf4j
@Component
public class MQConsumer {

    @Slf4j
    @Service
    @RocketMQMessageListener(topic = "test-topic-1", consumerGroup = "my-consumer_test-topic-1")
    public class MyConsumer1 implements RocketMQListener<String> {
        public void onMessage(String message) {
            log.info("received message: {}", message);
        }
    }

    @Slf4j
    @Service
    @RocketMQMessageListener(topic = "test-topic-2", consumerGroup = "my-consumer_test-topic-2")
    public class MyConsumer2 implements RocketMQListener<MQProducer.OrderPaidEvent>{
        public void onMessage(MQProducer.OrderPaidEvent orderPaidEvent) {
            log.info("received orderPaidEvent: {}", orderPaidEvent);
        }
    }

    @Service
    @RocketMQMessageListener(
            topic = "test-topic-1",
            consumerGroup = "my-consumer_test-topic-1",
//            enableMsgTrace = true,
            customizedTraceTopic = "my-trace-topic"
    )
    /**
     * Consumer 端消息轨迹的功能需要在 @RocketMQMessageListener 中进行配置对应的属性
     *
     * 默认情况下 Producer 和 Consumer 的消息轨迹功能是开启的 且 trace-topic 为 RMQ_SYS_TRACE_TOPIC
     * Consumer 端的消息轨迹 trace-topic 可以在配置文件中配置 rocketmq.consumer.customized-trace-topic 配置项，
     * 不需要为在每个 @RocketMQTransactionListener 配置
     */
    public class MyConsumer implements RocketMQListener<String> {

        @Override
        public void onMessage(String s) {
            log.info("received onMessage: {}", s);
        }
    }

}
