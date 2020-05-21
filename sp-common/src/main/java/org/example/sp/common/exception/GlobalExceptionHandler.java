package org.example.sp.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.sp.common.result.Result;
import org.example.sp.common.result.ResultEnum;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @Author: yizl
 * @Date: 2020/5/12
 * @Description: 全局异常拦截处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 功能描述: <br> 业务上的异常
     * @Param: [e]
     * @Return: Result
     * @Author: yizl
     * @Date: 2020/5/12 19:44
     */
    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e){
        log.error("[GlobalExceptionHandler][businessException] exception",e);
        return new Result<>(e.getResultEnum(),e.getClass());
    }



    /**
     * 功能描述: <br> 处理其他异常(数据库操作等)
     * @Param: [e]
     * @Return: org.example.sp.common.enetity.Result<?>
     * @Author: yizl
     * @Date: 2020/5/13 8:59
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e){
        log.error("[GlobalExceptionHandler][exception] exception",e);
        return new Result<>(ResultEnum.SYSTEM_EXECUTION_ERROR,e.getClass());
    }


    /**
     * 功能描述: <br>
     * @Param: [e]
     * @Return: org.example.sp.common.result.Result<?>
     * @Author: yizl
     * @Date: 2020/5/15 14:47
     */
    @ExceptionHandler({
            //请求url不存在(404)
            NoHandlerFoundException.class,
            //请求方法不正确,(get,post)
            HttpRequestMethodNotSupportedException.class,
            //请求头值不正确(content-type : application/json)
            HttpMediaTypeNotSupportedException.class,
            //未检测到路径参数(Restful风格,url上没有带参数)
            MissingPathVariableException.class,
            //缺少请求参数(服务端定义了参数,请求未带参数)
            MissingServletRequestParameterException.class,
            //参数类型匹配不正确(接收为Long类型,请求发得是字符串)
            TypeMismatchException.class,
            //与HttpMediaTypeNotSupportedException相反(已设置请求头,但是controller参数没有@RequestBody)
            HttpMessageNotReadableException.class,
            //序列化异常(请求体的json串,反序列化为pojo时报错)
            HttpMessageNotWritableException.class,
            // MethodArgumentNotValidException.class
            //客户端请求期望响应的媒体类型与服务器响应的媒体类型不一致(客户端设置 Accept: application/json,服务端返回的是字符串)
            HttpMediaTypeNotAcceptableException.class,
            //转化异常(例如:日期时间戳的装换)
            ConversionNotSupportedException.class,
            //找不到指定文件处理器
            MissingServletRequestPartException.class,
            //异步请求超时
            AsyncRequestTimeoutException.class
    })

    public Result<?> handleServletException(Exception e) {
        log.warn("[GlobalExceptionHandler][servletException] exception",e);
        return new Result<>(ResultEnum.REQUEST_PARAMETER_ERROR,e.getClass());
    }

}
