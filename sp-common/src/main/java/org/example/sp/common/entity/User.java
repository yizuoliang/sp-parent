package org.example.sp.common.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
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
@TableName("sp_user")
public class User {

    @TableId(type = IdType.UUID)
    @ApiModelProperty(value = "用户id")
    @ExcelProperty("用户id")
    private String id;

    @ApiModelProperty(value = "用户名")
    @ExcelProperty("用户名")
    private String userName;

    @ApiModelProperty(value = "用户密码")
    @ExcelProperty("用户密码")
    private String password;

    @ApiModelProperty(value = "姓名")
    @ExcelProperty("姓名")
    private String realName;

    /**
     * 盐值
     */
    private String salt;

    @ApiModelProperty(value = "身份证号码")
    @ExcelProperty("身份证号码")
    private String cardId;

    @ApiModelProperty(value = "性别")
    @ExcelProperty("性别")
    private  Integer sex;

    @ApiModelProperty(value = "状态")
    @ExcelProperty("状态")
    private  Integer status;

    @ApiModelProperty(value = "电话号码")
    @ExcelProperty("电话号码")
    private String phone;

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
