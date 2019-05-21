package org.light4j.sample.exception;

import lombok.extern.slf4j.Slf4j;
import org.light4j.sample.bean.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.executable.ValidateOnExecution;
import java.util.Optional;
import java.util.Set;

/**
 * 统一异常处理类
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandler {


    /**
     * 自定义异常捕捉器
     *
     * @param request
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(RestException.class)
    @ResponseBody
    public Response handlerRestException(HttpServletRequest request, RestException e) {
        Response response;
        RestCode respCode = e.getRespCode();
        respCode = Optional.ofNullable(respCode).orElse(RestCode.UNKNOWN_ERROR);
        log.error("ExceptionHandler.handlerRestException code:{},msg:{}", respCode.getCode(), respCode.getMsg());
        response = new Response(respCode.getCode(), respCode.getMsg());
        return response;
    }

    /**
     * 字段验证异常捕捉器
     *
     * @param exception
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(ValidationException.class)
    @ResponseBody
    public Response handerValidateException(ValidationException exception) {
        StringBuilder msg = new StringBuilder();
        if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) exception;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            for (ConstraintViolation<?> item : violations) {
                msg.append(item.getMessage());
            }
        }
        return new Response(RestCode.PARAM_ERROR.getCode(), msg.toString());
    }

    /**
     * 默认异常捕捉
     *
     * @param request
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Response<String> handlerException(HttpServletRequest request, RuntimeException e) {
        Response response;
        log.error("ExceptionHandler.handlerException:{}", e);
        response = new Response(RestCode.ERROR, e.getMessage());
        return response;
    }
}
