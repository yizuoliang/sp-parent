package org.example.sp.common.entity;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Author: yizl
 * @Date: 2020/6/1
 * @Description:
 */
public class JWTToken implements AuthenticationToken {

    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}