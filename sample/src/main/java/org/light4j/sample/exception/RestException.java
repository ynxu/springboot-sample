package org.light4j.sample.exception;

/**
 * 基础自定义异常捕捉类
 */
public class RestException extends RuntimeException {
    private RestCode respCode;

    public RestCode getRespCode() {
        return respCode;
    }

    public void setRespCode(RestCode respCode) {
        this.respCode = respCode;
    }
}
