package org.example.sp.common.result;

/**
 * @Author: yizl
 * @Date: 2020/5/12
 * @Description: 主要返回的code和msg枚举,参照阿里出品的java开发规范中的错误码
 */
public enum ResultEnum {

    SUCCESS("000000", "成功"),
    CLIENT_ERROR("A0001", "用户端错误"),
    REGISTRATION_ERROR("A0100", "用户注册错误"),
    LOGIN_EXCEPTION("A0200", "用户登录异常"),
    ACCESS_PERMISSION_EXCEPTION("A0300", "访问权限异常"),
    REQUEST_PARAMETER_ERROR("A0400", "请求参数错误"),
    REQUEST_SERVICE_EXCEPTION("A0500","用户请求服务异常"),
    SYSTEM_EXECUTION_ERROR("B0001","系统执行错误"),
    SYSTEM_EXECUTION_TIMEOUT("B0100","系统执行超时"),
    SYSTEM_RESOURCE_EXCEPTION("B0300","系统资源异常"),
    THIRD_PARTY_SERVICE_ERROR("C0001","调用第三方出错"),
    MIDDLEWARE_SERVICE_ERROR("C0100","中间件服务出错"),
    THIRD_PARTY_SERVICE_TIMEOUT("C0200","第三方系统执行超时"),
    DATABASE_ERROR("C0300","数据库服务出错");

    private String code;

    private String msg;

    private ResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
