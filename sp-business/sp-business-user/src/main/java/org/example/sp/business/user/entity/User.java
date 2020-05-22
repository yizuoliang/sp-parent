package org.example.sp.business.user.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: yizl
 * @Date: 2020/5/19
 * @Description:
 */
@Data
@ApiModel(value="用户对象", description="用户对象")
public class User {

    @TableId(type = IdType.UUID)
    @ApiModelProperty(value = "用户id")
    @ExcelProperty("用户id")
    private String id;

    @ApiModelProperty(value = "姓名")
    @ExcelProperty("姓名")
    private String name;

    @ApiModelProperty(value = "年龄")
    @ExcelProperty("年龄")
    private Integer age;

    @ApiModelProperty(value = "邮箱地址")
    @ExcelProperty("邮箱地址")
    private String email;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    @ExcelProperty("更新时间")
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    @ExcelProperty("创建时间")
    private Date createTime;


}
