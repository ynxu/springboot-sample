package org.light4j.sample.bean;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Table;
import org.light4j.sample.exception.RestCode;

import java.io.Serializable;

/**
 * 请求返回类
 *
 * @param <T>
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Response<T>", description = "同意请求返回类")
public class Response<T> implements Serializable {
    @ApiModelProperty(name = "code", value = "返回结果码", dataType = "int")
    private int code;
    @ApiModelProperty(name = "msg", value = "返回结果描述", dataType = "String")
    private String msg;
    @ApiModelProperty(name = "data", value = "返回体", dataType = "T")
    private T data;


    public Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(RestCode restCode) {
        this.code = restCode.getCode();
        this.msg = restCode.getMsg();
    }

    public Response(RestCode restCode, T data) {
        this.code = restCode.getCode();
        this.msg = restCode.getMsg();
        this.data = data;
    }

}
