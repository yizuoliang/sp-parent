package org.example.sp.common.enums;

/**
 * @Author: yizl
 * @Date: 2020/5/12
 * @Description: 返回类枚举
 */
public enum ResultEnum {
    //成功
    SUCCESS(200, "成功"),
    //失败
    FAIL(400, "失败"),
    //未进行登录
    UNAUTHORIZED(401, "未认证"),
    //接口不存在
    NOT_FOUND(404, "接口不存在"),
    //服务器错误
    SERVER_ERROR(500, "服务器错误");

    private Integer code;

    private String msg;

    private ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
