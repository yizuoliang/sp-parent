package org.example.sp.common.util;

import org.example.sp.common.enetity.Result;
import org.example.sp.common.enums.ResultEnum;

/**
 * @Author: yizl
 * @Date: 2020/5/12
 * @Description:
 */
public class ResultUtils {
    /**
     * 功能描述: <br> 成功返回类，不带参数
     * @Param: []
     * @Return: org.example.sp.common.enetity.Result<T>
     * @Author: yizl
     * @Date: 2020/5/12 18:27
     */
    public static <T> Result<T> success() {
        return success(null);
    }
    /**
     * 功能描述: <br>成功返回类，带参数
     * @Param: [t]
     * @Return: org.example.sp.common.enetity.Result<T>
     * @Author: yizl
     * @Date: 2020/5/12 18:28
     */
    public static <T> Result<T> success(T t) {
        Result<T> result = new Result<>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(t);
        return result;
    }
    /**
     * 功能描述: <br> 失败返回类，不带参数
     * @Param: []
     * @Return: org.example.sp.common.enetity.Result<T>
     * @Author: yizl
     * @Date: 2020/5/12 18:28
     */
    public static <T> Result<T> fail() {
        return fail(null);
    }
    /**
     * 功能描述: <br> 失败返回类，带参数
     * @Param: [t]
     * @Return: org.example.sp.common.enetity.Result<T>
     * @Author: yizl
     * @Date: 2020/5/12 18:29
     */
    public static <T> Result<T> fail(T t) {
        Result<T> result = new Result<>();
        result.setCode(ResultEnum.FAIL.getCode());
        result.setMsg(ResultEnum.FAIL.getMsg());
        result.setData(t);
        return result;
    }

}
