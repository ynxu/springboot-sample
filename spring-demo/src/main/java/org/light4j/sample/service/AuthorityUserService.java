package org.light4j.sample.service;

import org.light4j.sample.bean.Authority;
import org.light4j.sample.bean.User;
import org.light4j.sample.dao.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service(value = "userDetailService")
public class AuthorityUserService implements UserDetailsService {

    @Autowired
    UserJPA userJPA;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String lowerUsername = username.toLowerCase();

        User userFormDatabase = userJPA.findByUsernameCaseInsensitive(lowerUsername);
        if (userFormDatabase == null) {
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }

        // 获取用户所有权限并SpringSecurity需要的集合
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : userFormDatabase.getAuthorities()) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority.getName());
            grantedAuthorities.add(simpleGrantedAuthority);
        }
        // 返回springSecurity 需要的用户对象
        return new org.springframework.security.core.userdetails.User(
                userFormDatabase.getUsername(),
                userFormDatabase.getPassword(),
                grantedAuthorities
        );
    }

}
