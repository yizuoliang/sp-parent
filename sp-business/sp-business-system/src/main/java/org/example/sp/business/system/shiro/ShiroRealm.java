package org.example.sp.business.system.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.example.sp.business.system.service.IPermissionsService;
import org.example.sp.business.user.service.IUserService;
import org.example.sp.common.entity.User;
import org.example.sp.common.exception.SpAuthenticationException;
import org.example.sp.common.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Set;

/**
 * @Author: yizl
 * @Date: 2020/5/28
 * @Description:
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;

    @Autowired
    private IPermissionsService permissionsService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userName = null;
        if (principalCollection != null) {
            userName = principalCollection.toString();
        }
        //获取角色和权限
        Set<String> permissionSet = permissionsService.getPermissionsByUserName(userName);
        //设置用户所拥有的权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermissions(permissionSet);
        //把角色和权限获取出来,发到SimpleAuthorizationInfo授权对象中
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)  {

        //获取账号密码
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //从数据库中获取账号密码
        String userName=token.getUsername();
        // 通过用户名,从数据库中获取用户
        User user = userService.getByUserName(token.getUsername());
        if (user==null) {
            throw new SpAuthenticationException(ResultEnum.CLIENT_ACCOUNT_NON);
        }
        // 判断用户状态,如果为0,表示账号停用
       if (user.getStatus() == 0) {
           throw new SpAuthenticationException(ResultEnum.CLIENT_ACCOUNT_FREEZE);
        }
        //认证信息里存放账号密码, getName() 是当前Realm的继承方法,通常返回当前类名
        //盐也放进去
        return new SimpleAuthenticationInfo(userName,user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),getName());
    }
}
