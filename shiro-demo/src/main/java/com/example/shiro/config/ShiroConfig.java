package com.example.shiro.config;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //注入自定义的realm，告诉shiro如何获取用户信息来做登录或权限控制
    @Bean
    public Realm realm() {
        return new CustomRealm();
    }

    /**
     * 自定义的无状态（无session）Subject工厂
     */
    @Bean
    public StatelessSubjectFactory subjectFactory() {
        return new StatelessSubjectFactory();
    }

    /**
     * sessionManager通过sessionValidationSchedulerEnabled禁用掉会话调度器，
     * 因为我们禁用掉了会话，所以没必要再定期过期会话了。
     */
    @Bean
    public DefaultSessionManager sessionManager() {
        DefaultSessionManager sessionManager = new DefaultSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(false);

//        sessionManager.setCacheManager(shiroRedisCacheManager());
        return sessionManager;
    }

    /***
     * 安全管理配置
     */
    @Bean("securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 注意这里必须配置securityManager
        // 根据情况选择缓存器
//        securityManager.setCacheManager(shiroRedisCacheManager());// shiroEhCacheManager()

        // 设置Subject工厂
        securityManager.setSubjectFactory(subjectFactory());
        // 配置
        // session
        securityManager.setSessionManager(sessionManager());
        // 禁用Session作为存储策略的实现。
        DefaultSubjectDAO defaultSubjectDAO = (DefaultSubjectDAO) securityManager.getSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluatord = (DefaultSessionStorageEvaluator) defaultSubjectDAO
                .getSessionStorageEvaluator();
        defaultSessionStorageEvaluatord.setSessionStorageEnabled(false);
        securityManager.setRealm(realm());

//        SecurityUtils.setSecurityManager(securityManager);

        return securityManager;
    }

    /**
     * 配置shiro的拦截器链工厂,默认会拦截所有请求
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
//        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
//        // 配置安全管理(必须)
//        return filterFactoryBean;

        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        // 添加自己的过滤器取名为jwt
        Map<String, Filter> filterMap = new HashMap(16);
        filterMap.put("jwtFilter", statelessAuthcFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setSecurityManager(defaultWebSecurityManager());
        // 自定义url规则
        Map<String, String> filterRuleMap = new HashMap(16);
        // 所有请求通过我们自己的JWTFilter
        filterRuleMap.put("/**", "jwtFilter");
        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return factoryBean;
    }

    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        /**
         * setUsePrefix(false)用于解决一个奇怪的bug。在引入spring aop的情况下。
         * 在@Controller注解的类的方法中加入@RequiresRole注解，会导致该方法无法映射请求，导致返回404。
         * 加入这项配置能解决这个bug
         */
        creator.setUsePrefix(true);
        return creator;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager());
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public ShiroControlFilter statelessAuthcFilter() {
        return new ShiroControlFilter();
    }

    /**
     * 这里统一做鉴权，即判断哪些请求路径需要用户登录，哪些请求路径不需要用户登录。
     * 这里只做鉴权，不做权限控制，因为权限用注解来做。
     *
     * @return
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chain = new DefaultShiroFilterChainDefinition();
        //哪些请求可以匿名访问
        chain.addPathDefinition("/user/login", "anon");
        chain.addPathDefinition("/page/401", "anon");
        chain.addPathDefinition("/page/403", "anon");
        chain.addPathDefinition("/t5/hello", "anon");
        chain.addPathDefinition("/t5/guest", "anon");

        //除了以上的请求外，其它请求都需要登录
        chain.addPathDefinition("/**", "authc");
        return chain;
    }

}