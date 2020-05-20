package org.example.sp.business.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.sp.business.user.entity.User;
import org.example.sp.business.user.service.IUserService;
import org.example.sp.common.entity.PageQuery;
import org.example.sp.common.result.Result;
import org.example.sp.common.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: yizl
 * @Date: 2020/5/19
 * @Description:
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户控制类")
public class UserController {

    @Autowired
    private IUserService service;

    @GetMapping("/getUserById")
    @ApiOperation("通过id获取用户")
    public Result<User> getUserById(String id) {
        User user = service.getUserById(id);
        return new Result<>(ResultEnum.SUCCESS,user);
    }

    @PostMapping("/insertUser")
    @ApiOperation("添加用户")
    public Result<User> insertUser(@RequestBody User user) {
        service.insertUser(user);
        return new Result<>(ResultEnum.SUCCESS);
    }

    @PostMapping("/updateUserById")
    @ApiOperation("通过id更新用户")
    public Result<User> updateUserById(@RequestBody User user) {
        service.updateUserById(user);
        return new Result<>(ResultEnum.SUCCESS);
    }

    @PostMapping("/queryUserList")
    @ApiOperation("通过传入的条件分页查询")
    public Result<IPage<User>> queryUserList(@RequestBody PageQuery<User> pageUser ) {
        IPage<User> userPage=service.queryUserPage(pageUser);
        return new Result<>(ResultEnum.SUCCESS,userPage);
    }


}
