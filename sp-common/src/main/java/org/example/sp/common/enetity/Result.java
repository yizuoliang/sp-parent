package org.example.sp.common.enetity;

import lombok.Data;

/**
 * @Author: yizl
 * @Date: 2020/5/9
 * @Description: response统一返回类
 */
@Data
public class Result<T> {

    private Integer code;

    private String  msg;

    private T data;

}
