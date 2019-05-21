package com.light4j.sample.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.light4j.sample.SampleApplication;
import org.light4j.sample.bean.Response;
import org.light4j.sample.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTests {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void getUserById() {
        Response<User> forObject = testRestTemplate.getForObject("/user/100001", Response.class);
        System.out.println(forObject);
    }

}
