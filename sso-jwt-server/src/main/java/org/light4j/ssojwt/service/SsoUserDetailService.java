package org.light4j.ssojwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class SsoUserDetailService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if( !"admin".equals(s) )
            throw new UsernameNotFoundException("用户" + s + "不存在" );

        return new User( s, passwordEncoder.encode("123456"), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_NORMAL,ROLE_MEDIUM"));

    }

}
