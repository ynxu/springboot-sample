package com.light4j.sample.service;

import com.light4j.sample.BaseTests;
import org.junit.Test;
import org.light4j.sample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceTests extends BaseTests {
    @Autowired
    UserService userService;

    @Test
    public void testFindUserById() {
        long id = 100001;
//        userService.getUserById(id);
    }
}
