package org.example.sp.common.result;

/**
 * @Author: yizl
 * @Date: 2020/5/12
 * @Description: 主要返回的code和msg枚举,参照阿里出品的java开发规范中的错误码
 */
public enum ResultEnum {

    SUCCESS("000000", "成功"),
    CLIENT_ERROR("A0001", "用户端错误"),
    CLIENT_REGISTRATION_ERROR("A0100", "用户注册错误"),
    CLIENT_LOGIN_EXCEPTION("A0200", "用户登录异常"),
    CLIENT_ACCOUNT_NON("A0201", "用户账户不存在"),
    CLIENT_ACCOUNT_FREEZE("A0202", "用户账户被冻结"),
    CLIENT_PASSWORD_ERROR("A0210", "用户密码错误"),
    CLIENT_PERMISSION_EXCEPTION("A0300", "访问权限异常"),
    CLIENT_PERMISSION_UNAUTHORIZED("A0301", "访问未授权"),
    CLIENT_PARAMETER_ERROR("A0400", "请求参数错误"),
    CLIENT_SERVICE_EXCEPTION("A0500","用户请求服务异常"),
    SYSTEM_EXECUTION_ERROR("B0001","系统执行错误"),
    SYSTEM_EXECUTION_TIMEOUT("B0100","系统执行超时"),
    SYSTEM_RESOURCE_EXCEPTION("B0300","系统资源异常"),
    PROVIDER_SERVICE_ERROR("C0001","调用第三方出错"),
    PROVIDER_MIDDLEWARE_ERROR("C0100","中间件服务出错"),
    PROVIDER_SERVICE_TIMEOUT("C0200","第三方系统执行超时"),
    PROVIDER_DATABASE_ERROR("C0300","数据库服务错误"),
    PROVIDER_QUARTZ_ERROR("C0600","Quartz框架错误"),
    PROVIDER_QUARTZ_SAVE_FAIL("C0601","Quartz添加调度失败"),
    PROVIDER_QUARTZ_UPDATE_FAIL("C0602","Quartz更新调度失败"),
    PROVIDER_QUARTZ_DELETE_FAIL("C0603","Quartz删除调度失败"),
    PROVIDER_QUARTZ_PAUSE_FAIL("C0604","Quartz暂停调度失败"),
    PROVIDER_QUARTZ_RESUME_FAIL("C0604","Quart恢复调度失败");


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
