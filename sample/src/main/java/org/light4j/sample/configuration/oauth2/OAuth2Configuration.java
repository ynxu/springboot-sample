package org.light4j.sample.configuration.oauth2;

import org.light4j.sample.enums.Authorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
import java.util.Optional;

/**
 * 我们在OAuth2Configuration配置类中添加子类ResourceServerConfiguration继承自ResourceServerConfigurerAdapter完成资源服务器的配置，
 * <p>
 * 使用@EnableResourceServer注解来开启资源服务器，
 * <p>
 * 因为整合SpringSecurity的缘故，我们需要配置登出时清空对应的access_token控制以及自定义401错误内容（authenticationEntryPoint），
 * <p>
 * 在配置类中我们排除了对/hello公开地址拦截以及/secure下的所有地址都必须授权才可以访问。
 */
@Configuration
public class OAuth2Configuration {

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Autowired
        CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

        @Autowired
        CustomLogoutSuccessHandler customLogoutSuccessHandler;

        @Override
        public void configure(HttpSecurity http) throws Exception {
//            super.configure(http);
            http.exceptionHandling()
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
                    .and().logout().
                    logoutUrl("/oauth/logout").logoutSuccessHandler(customLogoutSuccessHandler)
                    .and().authorizeRequests()
                    .antMatchers("/sample/", "/**").permitAll()
                    .antMatchers("/secure/**").authenticated();
        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter implements EnvironmentAware {

        private static final String ENV_OAUTH = "authentication.oauth.";
        private static final String PROP_CLIENT_ID = ENV_OAUTH + "client-id";
        private static final String PROP_SECRET = ENV_OAUTH + "secret";
        private static final String PROP_TOKEN_VALIDITY_SECONDS = ENV_OAUTH + "token-validity-in-seconds";

        private Binder binder;

        @Autowired
        DataSource dataSource;

        @Bean
        public TokenStore tokenStore() {
            return new JdbcTokenStore(dataSource);
        }

        @Autowired
//        @Qualifier(value = "authenticationMangerBean")
                AuthenticationManager authenticationManager;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//            super.configure(endpoints);
            endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager);
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
            super.configure(security);
            security.allowFormAuthenticationForClients();
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//            super.configure(clients);
            clients
                    .inMemory()
                    .withClient(binder.bind(PROP_CLIENT_ID, String.class).get())
                    .scopes("read", "write")
                    .authorities(Authorities.ROLE_ADMIN.name(), Authorities.ROLE_USER.name())
                    .authorizedGrantTypes("password", "refresh_token")
                    .secret(binder.bind(PROP_SECRET, String.class).get())
                    .accessTokenValiditySeconds(binder.bind(PROP_TOKEN_VALIDITY_SECONDS, Integer.class).orElse(1800));
        }

        @Override
        public void setEnvironment(Environment environment) {
            this.binder = Binder.get(environment);
        }

    }

}
