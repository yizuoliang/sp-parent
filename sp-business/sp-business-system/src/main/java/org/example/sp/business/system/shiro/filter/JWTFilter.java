package org.example.sp.business.system.shiro.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.example.sp.business.user.service.IUserService;
import org.example.sp.common.entity.JWTToken;
import org.example.sp.common.exception.SpAuthenticationException;
import org.example.sp.common.result.Result;
import org.example.sp.common.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @Author: yizl
 * @Date: 2020/6/1
 * @Description: 鉴权登录拦截器
 */
@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {

    @Autowired
    private IUserService userService;

    /**
     * 功能描述: <br> 执行jwt认证,
     * @Param: [request, response, mappedValue]
     * @Return: boolean
     * @Author: yizl
     * @Date: 2020/6/1 11:29
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        executeLogin(request, response);
        return true;
    }


    /**
     * 功能描述: <br> 执行登录,将header中的token,放入shiro realm中校验
     * @Param: [request, response]
     * @Return: boolean
     * @Author: yizl
     * @Date: 2020/6/1 11:33
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        //将token修改到cookie中获取
        String jwtToken=null;
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies!=null){
            for (Cookie cookie:cookies) {
                if (JWTUtil.COOKIE_NAME.equals(cookie.getName())){
                    jwtToken =cookie.getValue();
                }
            }
        }
        //httpServletRequest.getHeader("Authorization");
        JWTToken token = new JWTToken(jwtToken);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        try {
            getSubject(request, response).login(token);
        } catch (SpAuthenticationException se) {
            PrintWriter out=null;
            try {
                res.setCharacterEncoding("UTF-8");
                res.setContentType("application/json");
                out = res.getWriter();
                ObjectMapper mapper = new ObjectMapper();
                out.println(mapper.writeValueAsString(new Result<>(se.getResultEnum())));
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if (null != out) {
                    out.flush();
                    out.close();
                }
            }
        }
        return true;
    }

}
