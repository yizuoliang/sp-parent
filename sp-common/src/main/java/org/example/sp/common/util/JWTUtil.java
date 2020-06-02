package org.example.sp.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @Author: yizl
 * @Date: 2020/6/1
 * @Description: JWT的工具类
 */
public class JWTUtil {

    // Token过期时间,60分钟
    public static final long EXPIRE_TIME = 60*60 * 1000;

    // Token刷新时间,30分钟
    public static final long REFRESH_TIME = 30*60 * 1000;

    //cookie名字
    public static final String COOKIE_NAME="jwt_token";

    /**
     * 功能描述: <br> 从token中解析出来userName
     * @Param: [token]
     * @Return: java.lang.String
     * @Author: yizl
     * @Date: 2020/6/1 13:54
     */
    public static String getUserName(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userName").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
    /**
     * 功能描述: <br> 校验token是否需要刷新
     * @Param: [token]
     * @Return: boolean
     * @Author: yizl
     * @Date: 2020/6/2 14:38
     */
    public static boolean isRefreshToken(String token){
        long expiresAt = getExpiresAt(token).getTime();
        long currentTimeMillis = System.currentTimeMillis();
        if (expiresAt-currentTimeMillis <= REFRESH_TIME){
            return true;
        }
        return false;
    }


    /**
     * 功能描述: <br> 获取token中的签证过期时间
     * @Param: [token]
     * @Return: java.util.Date
     * @Author: yizl
     * @Date: 2020/6/2 14:34
     */
    public static Date getExpiresAt(String token) {
        DecodedJWT jwt = JWT.decode(token);
        Date expiresAt = jwt.getExpiresAt();
        return expiresAt;
    }

    /**
     * 功能描述: <br> 校验token是否正确
     * @Param: [token, userName, secret] secret是数据库加密后的密码
     * @Return: boolean
     * @Author: yizl
     * @Date: 2020/6/1 14:02
     */
    public static boolean verify(String token, String userName, String secret) {
        try {
            //根据数据库密码生成JWT的校验器
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("userName", userName).build();
            DecodedJWT verify = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }

    }
    /**
     * 功能描述: <br> 生成签名,生成jwt
     * @Param: [username, secret] 用户名 密码(数据库加密后的密码)
     * @Return: java.lang.String
     * @Author: yizl
     * @Date: 2020/6/1 14:36
     */
    public static String sign(String username, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = null;
        try {
            //私钥和加密算法
            algorithm = Algorithm.HMAC256(secret);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 附带username信息
        return JWT.create().withClaim("userName", username).withExpiresAt(date).sign(algorithm);
    }

}
