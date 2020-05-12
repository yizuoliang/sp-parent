package org.example.sp.business.hello.world.controller;


import io.swagger.annotations.ApiOperation;
import org.example.sp.business.hello.world.entity.User;
import org.example.sp.business.hello.world.mapper.UserMapper;
import org.example.sp.common.enetity.Result;
import org.example.sp.common.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yizl
 */

@RestController
@RequestMapping("/hello")
public class HelloWorldController {

    @Autowired
    private UserMapper mapper;

    @GetMapping("/world")
    public String helloWorld(){
        return "Hello World";
    }

    @ApiOperation("获取所有用户列表")
    @GetMapping("/getUserList")
    public Result<List<User>> getUserList(){
        return ResultUtils.success(mapper.selectList(null));
    }

    @ApiOperation("通过id获取用户")
    @GetMapping("/getUserById")
    public Result<User> getUserById(String id){
        return ResultUtils.success(mapper.selectById(id));
    }

}
