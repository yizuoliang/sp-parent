package org.example.sp.common.exception;

import org.example.sp.common.result.ResultEnum;

/**
 * @Author: yizl
 * @Date: 2020/5/12
 * @Description: 自定义业务运行异常类
 */
public class BusinessException extends RuntimeException {

    private ResultEnum resultEnum;

    public BusinessException(ResultEnum resultEnum)
    {
        this.resultEnum=resultEnum;
    }

    public ResultEnum getResultEnum(){
        return this.resultEnum;
    }
}
