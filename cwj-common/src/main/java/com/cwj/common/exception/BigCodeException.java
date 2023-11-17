package com.cwj.common.exception;

public enum BigCodeException {
    VALID_EXCEPTION(10001,"参数格式校验失败");
    private int code;
    private String msg;
    BigCodeException(int code,String msg){
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
