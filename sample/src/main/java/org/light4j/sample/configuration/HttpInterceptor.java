package org.light4j.sample.configuration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.light4j.sample.bean.HttpLogger;
import org.light4j.sample.util.HttpLoggerUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.light4j.sample.util.HttpLoggerUtils.LOGGER_entry;
import static org.light4j.sample.util.HttpLoggerUtils.LOGGER_return;

/**
 * 网络请求拦截 , 拦截请求和响应, 封装 HttpLogger
 *
 * @see HttpLogger
 */
@Slf4j
@Configuration
public class HttpInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 创建请求日志实体
        HttpLogger httpLogger = new HttpLogger();
        // 获取请求session
        String requestedSessionId = request.getRequestedSessionId();
        // 请求路径
        String requestURI = request.getRequestURI();
        // 请求参数
        String paramData = JSON.toJSONString(request.getParameterMap(), SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue);
        // 设置客户端IP
        httpLogger.setClientIp(HttpLoggerUtils.getCliectIp(request));
        // 设置请求方法
        httpLogger.setMethod(request.getMethod());
        // 设置请求类型
        httpLogger.setType(HttpLoggerUtils.getRequestType(request));
        // 设置请求参数
        httpLogger.setParamData(paramData);
        // 设置请求地址
        httpLogger.setUri(requestURI);
        httpLogger.setSessionId(requestedSessionId);
        // 设置请求开始时间
        httpLogger.setTime(System.currentTimeMillis());
        // 设置q请求日志实体
        request.setAttribute(LOGGER_entry, httpLogger);
        return super.preHandle(request, response, handler);

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
        // 获取请求状态
        int status = response.getStatus();
        // 当前时间
        long current = System.currentTimeMillis();
        // 获取返回值
        String returnData = JSON.toJSONString(request.getAttribute(LOGGER_return), SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue);
        HttpLogger httpLogger = (HttpLogger) request.getAttribute(LOGGER_entry);

        // 请求开始时间
        Long send_time = httpLogger.getTime();
        // 设置请求结束时间
        httpLogger.setReturnTime(current);
        httpLogger.setHttpStatusCode(status);
        httpLogger.setReturnData(returnData);

        log.info("httpLogger:{}", httpLogger);

    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
