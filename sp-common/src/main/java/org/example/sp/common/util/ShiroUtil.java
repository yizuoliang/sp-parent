package org.example.sp.common.util;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.example.sp.common.constant.ShiroConstant;

import java.util.Random;

/**
 * @Author: yizl
 * @Date: 2020/5/29
 * @Description:
 */
public class ShiroUtil {

    /**
     * 功能描述: <br> 对密码进行加密
     * @Param: [password, salt]
     * @Return: java.lang.String
     * @Author: yizl
     * @Date: 2020/5/29 15:22
     */
    public static String md5(String password, String salt){
        //盐：为了即使相同的密码不同的盐加密后的结果也不同
        ByteSource byteSalt = ByteSource.Util.bytes(salt);
        //密码
        Object source = password;
        //加密次数
        SimpleHash result = new SimpleHash(ShiroConstant.ALGORITHM_NAME, source, byteSalt, ShiroConstant.ENCODE_COUNT);
        return result.toString();
    }

    /**
     * 功能描述: <br>
     * @Param:  获取8位随机数,作为盐值
     * @Return: java.lang.String
     * @Author: yizl
     * @Date: 2020/5/29 15:41
     */
    public static  String getRandomSalt(){
        StringBuffer sb = new StringBuffer();
        Random rd = new Random();
        for(int i=0;i < 8 ;i++) {
            sb.append(ShiroConstant.RANDOM_BASIC_VALUE.charAt(rd.nextInt(ShiroConstant.RANDOM_BASIC_VALUE.length())));
        }
        return sb.toString();
    }
}
