package org.example.sp.common.enetity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: yizl
 * @Date: 2020/5/9
 * @Description: response统一返回类
 */
@Data
@ApiModel(value="接口返回对象", description="接口返回对象")
public class Result<T> {

    @ApiModelProperty(value = "返回code")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String  msg;

    @ApiModelProperty(value = "返回数据对象")
    private T data;

}
