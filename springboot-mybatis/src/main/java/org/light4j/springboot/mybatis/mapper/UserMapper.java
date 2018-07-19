package org.light4j.springboot.mybatis.mapper;

import org.light4j.springboot.mybatis.domain.User;
import org.light4j.springboot.mybatis.mapper.provider.UserSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface UserMapper {
    /**
     * 使用注解编写SQL
     * @return
     */
    @Select("select * from user where 1=1")
    List<User> list();

    /**
     * 使用工具类方法动态编写SQL
     * @return
     */
    @SelectProvider(type = UserSqlProvider.class , method = "list2")
    List<User> list2();

    /**
     * 延伸：上述两种方式都可以附加@Results注解来指定结果集的映射关系.
     *
     * PS：如果符合下划线转驼峰的匹配项可以直接省略不写。
     */
    @Results({
            @Result(property = "userId", column = "userid"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
    })
    @Select("select * from user")
    List<User> listSample();

    /**
     * 延伸：无论什么方式,如果涉及多个参数,则必须加上@Param注解,否则无法使用EL表达式获取参数。
     */
    @Select("select * from user where username like #{username} and password like #{password}")
    User get(@Param("username") String username, @Param("password") String password);

    @SelectProvider(type = UserSqlProvider.class, method = "getBadUser")
    User getBadUser(@Param("username") String username, @Param("password") String password);

    @Results({
            @Result(property = "username" , column = "username")
    })
    @Select("select * from user where username like #{username}")
    List<User> findByUsername(String username);

    @Results({
            @Result(property = "userId" , column = "userid")
    })
    @Select("select * from user where userid like #{userId}")
    User getOne(String userId);

    @Results({
            @Result(property = "userId" , column = "userid")
    })
    @Delete("delete from user where userid like #{userId}")
    int delete(String userId);

}
