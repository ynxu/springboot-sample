package org.light4j.sample.service;

import org.light4j.sample.bean.User;
import org.light4j.sample.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "user")
public class UserService {

    @Autowired
    UserMapper userMapper;


    @Cacheable
    public User getUserById(long id) {
        User user = userMapper.getUserById(id);
        return user;
    }
}
