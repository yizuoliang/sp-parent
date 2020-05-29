package org.example.sp.business.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.example.sp.business.system.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Author: yizl
 * @Date: 2020/5/28
 * @Description:
 */
@Repository
public interface PermissionMapper extends BaseMapper<Permission>  {

    @Select("SELECT p.* FROM `sp_user` u JOIN sp_user_role ur ON u.id=ur.user_id " +
            " JOIN sp_role r on ur.role_id=r.id JOIN sp_role_permission rp on r.id=rp.role_id " +
            "JOIN sp_permission p on rp.permission_id=p.id where u.user_name= #{userName} ")
    List<Permission> queryPermissionByUserName(String userName);

}
