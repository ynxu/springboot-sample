package org.light4j.sample.configuration.oauth2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
/**
 * 自定义401错误码内容
 *
 * 来配置如果没有权限访问接口时我们返回的错误码以及错误内容
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        log.info("pre-authentication entry point called !");
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access denied");
    }
}
