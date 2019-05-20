package org.light4j.sample.bean;


import lombok.Data;

import java.io.Serializable;

/**
 * 网络请求日志实体
 */
@Data
public class HttpLogger implements Serializable {
    private Long id;
    private String uri;
    private String clientIp;
    private String type;
    private String method;
    private String paramData;
    private String sessionId;
    private Long time;
    private Long returnTime;
    private int httpStatusCode;
    private String returnData;

}
