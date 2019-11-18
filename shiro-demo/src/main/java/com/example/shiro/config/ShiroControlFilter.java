package com.example.shiro.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@Slf4j
public class ShiroControlFilter extends AccessControlFilter {

    /**
     * 先执行：isAccessAllowed 再执行onAccessDenied
     * <p>
     * isAccessAllowed：表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，
     * 如果允许访问返回true，否则false；
     * <p>
     * 如果返回true的话，就直接返回交给下一个filter进行处理。 如果返回false的话，回往下执行onAccessDenied
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        return false;
    }

    /**
     * onAccessDenied：表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；
     * 如果返回false表示该拦截器实例已经处理了，将直接返回即可。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //==============================该步骤主要是通过token代理登录shiro======================
        HttpServletRequest req = (HttpServletRequest) request;
        String name = req.getHeader("name");
        String pwd = req.getHeader("pwd");
        UsernamePasswordToken statelessAuthenticationToken = new UsernamePasswordToken(name, pwd);
        try {
            // 委托给Realm进行登录
            getSubject(request, response).login(statelessAuthenticationToken);
        } catch (UnknownAccountException | IncorrectCredentialsException ue) {
            log.debug(ue.getLocalizedMessage());
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            //登录失败不用处理,后面的过滤器会处理并且能通过@ControllerAdvice统一处理相关异常
        }
        return true;
    }
}
