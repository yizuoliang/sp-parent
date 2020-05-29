package org.example.sp.common.exception;


import org.apache.shiro.authc.AuthenticationException;
import org.example.sp.common.result.ResultEnum;

/**
 * @Author: yizl
 * @Date: 2020/5/28
 * @Description: sp系统权限认证异常类
 */
public class SpAuthenticationException extends AuthenticationException {

    private ResultEnum resultEnum;

    public SpAuthenticationException(ResultEnum resultEnum)
    {
        this.resultEnum=resultEnum;
    }

    public ResultEnum getResultEnum(){
        return this.resultEnum;
    }
}
