package org.light4j.springboot.mybatis.mapper.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * 主要用途：根据复杂的业务需求来动态生成SQL.
 * <p>
 * 目标：使用Java工具类来替代传统的XML文件.(例如：UserSqlProvider.java <-- UserMapper.xml)
 */
public class UserSqlProvider {

    /** 方式1：在工具类的方法里,可以自己手工编写SQL。 */
    public String list2() {
        return "select * from user";
    }

    /** 方式2: 使用官方提供的API来编写动态SQL */
    public String getBadUser(@Param("username") final String username, @Param("password") final String password) {
        SQL sql = new SQL() {
            {
                SELECT("*");
                FROM("user");
                if (username != null && password != null) {
                    WHERE("username like #{username} and password like #{password}");
                }else{
                    WHERE("1=2");
                }
            }
        };
        return sql.toString();
    }
}
