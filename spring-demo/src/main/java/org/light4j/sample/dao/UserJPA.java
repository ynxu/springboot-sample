package org.light4j.sample.dao;

import org.apache.ibatis.annotations.Param;
import org.light4j.sample.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJPA extends JpaRepository<User, Long> {

    @Query("select u from User u where lower(u.username) = lower(:username) ")
    User findByUsernameCaseInsensitive(@Param("username") String username);
}
