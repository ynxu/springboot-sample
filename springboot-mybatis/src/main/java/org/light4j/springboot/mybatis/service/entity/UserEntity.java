package org.light4j.springboot.mybatis.service.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "user")
public class UserEntity {
    @Id
    @Column(name = "userid")
    private String userId;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Transient
    private String other; //跟数据库无关的字段使用@Transient标记或移至VO类。

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", other='" + other + '\'' +
                '}';
    }
}
