package org.example.sp.business.system.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.example.sp.business.system.service.IPermissionsService;
import org.example.sp.business.user.service.IUserService;
import org.example.sp.common.entity.JWTToken;
import org.example.sp.common.entity.User;
import org.example.sp.common.exception.SpAuthenticationException;
import org.example.sp.common.result.ResultEnum;
import org.example.sp.common.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * @Author: yizl
 * @Date: 2020/5/28
 * @Description:
 */
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;

    @Autowired
    private IPermissionsService permissionsService;

    @Autowired
    private HttpServletResponse response;

    
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 功能描述: <br> 权限控制
     * @Param: [principalCollection]
     * @Return: org.apache.shiro.authz.AuthorizationInfo
     * @Author: yizl
     * @Date: 2020/6/1 15:22
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String token = null;
        if (principalCollection != null) {
            token = principalCollection.toString();
        }
        //根据token获取权限授权
        String userName = JWTUtil.getUserName(token);
        //获取角色和权限
        Set<String> permissionSet = permissionsService.getPermissionsByUserName(userName);
        //设置用户所拥有的权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermissions(permissionSet);
        //把角色和权限获取出来,发到SimpleAuthorizationInfo授权对象中
        return authorizationInfo;
    }

    /**
     * 功能描述: <br> 校验接口携带的JWT是否正确,登录接口不需要经过此认证
     * @Param: [authenticationToken]
     * @Return: org.apache.shiro.authc.AuthenticationInfo
     * @Author: yizl
     * @Date: 2020/6/1 13:47
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)  {
        String token = (String) authenticationToken.getCredentials();
        if (token == null) {
            throw new SpAuthenticationException(ResultEnum.CLIENT_TOKEN_INVALID);
        }
        //验证jwt的有效性
        String userName = JWTUtil.getUserName(token);
        if (userName == null) {
            throw new SpAuthenticationException(ResultEnum.CLIENT_TOKEN_INVALID);
        }
        // 查询用户信息
        User user = userService.getByUserName(userName);
        if (user==null) {
            throw new SpAuthenticationException(ResultEnum.CLIENT_TOKEN_INVALID);
        }

        //校验密码是否正确
        boolean verify = JWTUtil.verify(token, userName, user.getPassword());
        if (!verify){
            throw new SpAuthenticationException(ResultEnum.CLIENT_TOKEN_INVALID);
        }

        // 判断用户状态,如果为0,表示账号停用
        if (user.getStatus() == 0) {
            throw new SpAuthenticationException(ResultEnum.CLIENT_TOKEN_INVALID);
        }
        //判断是否要刷新token
       if (JWTUtil.isRefreshToken(token)){
            //重新签发token,保存在cookie中
            String sign = JWTUtil.sign(userName, user.getPassword());
            Cookie cookie = new Cookie(JWTUtil.COOKIE_NAME, sign);
            cookie.setPath("/sp");
            response.addCookie(cookie);
        }
        return new SimpleAuthenticationInfo(user,token,getName());
    }
}
