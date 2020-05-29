package org.example.sp.business.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.example.sp.common.entity.User;
import org.example.sp.common.exception.SpAuthenticationException;
import org.example.sp.common.result.Result;
import org.example.sp.common.result.ResultEnum;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yizl
 * @Date: 2020/5/28
 * @Description: 登录相关接口
 */
@RestController
@RequestMapping("/login")
@Api(tags = "登录控制类")
public class LoginController {

    /**
     * 登录
     */
    @PostMapping("/auth")
    @ApiOperation("用户登录接口")
    public Result<?>  authLogin(@RequestBody User user) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());
        try {
            subject.login(token);
        } catch (IncorrectCredentialsException e) {
            throw new SpAuthenticationException(ResultEnum.CLIENT_PASSWORD_ERROR);
        }
        return new Result<>(ResultEnum.SUCCESS);
    }

    /**
     * 注销
     */
    @PostMapping("/logout")
    @ApiOperation("用户注销接口")
    public Result<?>  logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new Result<>(ResultEnum.SUCCESS);
    }
}
