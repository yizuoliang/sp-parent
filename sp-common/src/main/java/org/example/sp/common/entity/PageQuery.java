package org.example.sp.common.entity;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: yizl
 * @Date: 2020/5/15
 * @Description: 分页查询类
 */
@Data
@ApiModel(value="查询分页对象", description="查询分页对象")
public class PageQuery<T>  {
    /**
     * 分页类
     */
    @ApiModelProperty(value = "分页对象")
    private Page<T> page;
    /**
     * 查询参数
     */
    @ApiModelProperty(value = "查询对象参数")
    private T params;

}
