package org.light4j.sample.dao;

import org.apache.ibatis.annotations.*;
import org.light4j.sample.bean.User;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {

    @SelectProvider(type = UserProvider.class,method = "getUserById")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
    })
    User getUserById(@Param("id") long id);

}
