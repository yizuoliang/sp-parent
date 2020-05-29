package org.example.sp.business.system.service.impl;

import org.example.sp.business.system.Permission;
import org.example.sp.business.system.mapper.PermissionMapper;
import org.example.sp.business.system.service.IPermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: yizl
 * @Date: 2020/5/28
 * @Description: 权限实现类
 */
@Service
public class PermissionsServiceImpl implements IPermissionsService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Set<String> getPermissionsByUserName(String userName) {
        Set<String> permissionSet = new HashSet<>();
        List<Permission> permissionList = permissionMapper.queryPermissionByUserName(userName);
        for (Permission permission : permissionList) {
            permissionSet.add(permission.getUrl());
        }
        return permissionSet;
    }
}
