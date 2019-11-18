package org.light4j.sample.dao;

import org.apache.ibatis.jdbc.SQL;

public class UserProvider {

    public static String getUserById(long id) {
        return new SQL() {{
            SELECT("id,username,password");
            FROM("user");
            WHERE("id=#{id}");
        }}.toString()+" limit 1";
    }

    public static String getUserByUsername(String username) {
        return new SQL() {{
            SELECT("*");
            FROM("user");
            WHERE("username=#{username}");
        }}.toString() + " limit 1";
    }
}
