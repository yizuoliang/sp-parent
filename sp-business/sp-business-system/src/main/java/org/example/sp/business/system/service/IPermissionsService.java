package org.example.sp.business.system.service;

import java.util.Set;

/**
 * @Author: yizl
 * @Date: 2020/5/28
 * @Description: 权限接口
 */
public interface IPermissionsService {

    Set<String> getPermissionsByUserName(String userName);
}
