package com.ynxu.spring.rocketmq;

import com.ynxu.spring.rocketmq.producer.MQProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RocketMQApplication.class})
public class MQTests {

    @Autowired
    MQProducer producer;

    @Test
    public void testProducer(){
        for (int i = 0; i < 1; i++) {
            producer.sendMessage("Hello RocketMQ " + i, "TopicTest", "TagTest", "key" + i);
        }
        try {
            Thread.sleep(10 * 1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
