package com.licc.dove.web.exception;
/**
* @author lichangchao
* @Description:
* @date  2017/12/28
*/
public enum ConstException {
    // 未知错误
    UNKONW_ERROR(-1,"系统异常"),
    //成功
    SUCCESS(0,"成功"),
    //用户名或密码错误
    BIZ_ERROR(1,"业务异常"),
    VALID_ERROR(2,null),
    ;
    private Integer code;

    private String msg;

    ConstException(Integer code,String msg) {
        this.code = code;
        this.msg=msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}