package org.example.sp.business.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.example.sp.business.user.service.IUserService;
import org.example.sp.common.entity.JWTToken;
import org.example.sp.common.entity.User;
import org.example.sp.common.exception.SpAuthenticationException;
import org.example.sp.common.result.Result;
import org.example.sp.common.result.ResultEnum;
import org.example.sp.common.util.JWTUtil;
import org.example.sp.common.util.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: yizl
 * @Date: 2020/5/28
 * @Description: 登录相关接口
 */
@RestController
@RequestMapping("/login")
@Api(tags = "登录控制类")
public class LoginController {

    @Autowired
    private IUserService userService;

    @Autowired
    private HttpServletResponse response;

    /**
     * 功能描述: <br> 登录接口,与shiro无关了,单独验证
     * @Param: [loginUser]
     * @Return: org.example.sp.common.result.Result<?>
     * @Author: yizl
     * @Date: 2020/6/1 14:52
     */
    @PostMapping("/auth")
    @ApiOperation(value="用户登录接口",notes="用户登录签发JWT")
    public Result<?>  authLogin(@RequestBody User loginUser) {
        String userName = loginUser.getUserName();
        String password = loginUser.getPassword();
        User user = userService.getByUserName(userName);
        if (user==null) {
            throw new SpAuthenticationException(ResultEnum.CLIENT_ACCOUNT_NON);
        }
        //生成密码与数据库密码比较
        password = ShiroUtil.md5(password, user.getSalt());
        //密码不正确
        if(!user.getPassword().equals(password)){
            throw new SpAuthenticationException(ResultEnum.CLIENT_PASSWORD_ERROR);
        }
        // 判断用户状态,如果为0,表示账号停用
        if (user.getStatus() == 0) {
            throw new SpAuthenticationException(ResultEnum.CLIENT_ACCOUNT_FREEZE);
        }
        String sign = JWTUtil.sign(user.getUserName(), user.getPassword());
        Cookie cookie = new Cookie(JWTUtil.COOKIE_NAME, sign);
        cookie.setPath("/sp");
        response.addCookie(cookie);
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
        //将cookie更新为空
        Cookie cookie = new Cookie(JWTUtil.COOKIE_NAME, null);
        cookie.setPath("/sp");
        response.addCookie(cookie);
        return new Result<>(ResultEnum.SUCCESS);
    }
}
