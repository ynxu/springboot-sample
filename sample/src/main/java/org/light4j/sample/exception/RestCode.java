package org.light4j.sample.exception;

public enum RestCode {
    SUCCESS(200, "成功"),
    ERROR(700, "失败"),
    UNKNOWN_ERROR(701, "未知错误"),
    UPLOAD_FILE_FAILD(702, "上传文件失败"),
    PARAM_ERROR(703, "参数不合法"),
    ;

    private int code;
    private String msg;

    RestCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
