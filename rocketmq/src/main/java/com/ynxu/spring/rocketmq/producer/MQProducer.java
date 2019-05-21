package com.ynxu.spring.rocketmq.producer;


import com.ynxu.spring.rocketmq.domain.OrderPaidEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;


@Slf4j
@Component
public class MQProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void send() {
        rocketMQTemplate.convertAndSend("test-topic-1", "Hello, World!");
        rocketMQTemplate.send("test-topic-1", MessageBuilder.withPayload("Hello, World! I'm from spring message").build());
        rocketMQTemplate.convertAndSend("test-topic-2", new OrderPaidEvent("T_001", new BigDecimal("88.00")));

//        rocketMQTemplate.destroy(); // notes:  once rocketMQTemplate be destroyed, you can not send any message again with this rocketMQTemplate
    }

    public void sendTransaction(){
        try {
            // Build a SpringMessage for sending in transaction
            Message msg = MessageBuilder.withPayload(new Message() {
                @Override
                public Object getPayload() {
                    return null;
                }

                @Override
                public MessageHeaders getHeaders() {
                    return null;
                }
            }).build();
            // In sendMessageInTransaction(), the first parameter transaction name ("test")
            // must be same with the @RocketMQTransactionListener's member field 'transName'
            TransactionSendResult test = rocketMQTemplate.sendMessageInTransaction("test", "test-topic", msg, null);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }


    /**
     * Define transaction listener with the annotation @RocketMQTransactionListener
     *
     * 事务消息的发送需要在 @RocketMQTransactionListener 注解里配置上 AK/SK:
     */
    @RocketMQTransactionListener(
            txProducerGroup="test",
            accessKey = "AK",
            secretKey = "SK")
    class TransactionListenerImpl implements RocketMQLocalTransactionListener {
        @Override
        public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
            // ... local transaction process, return bollback, commit or unknown
            return RocketMQLocalTransactionState.UNKNOWN;
        }

        @Override
        public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
            // ... check transaction status and return bollback, commit or unknown
            return RocketMQLocalTransactionState.COMMIT;
        }
    }

}
