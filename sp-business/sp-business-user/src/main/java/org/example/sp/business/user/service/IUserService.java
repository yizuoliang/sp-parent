package org.example.sp.business.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.sp.business.user.entity.User;
import org.example.sp.common.entity.PageQuery;

/**
 * @Author: yizl
 * @Date: 2020/5/19
 * @Description:
 */
public interface IUserService {

    User getUserById(String id);

    void insertUser(User user);

    void updateUserById(User user);

    IPage<User> queryUserPage(PageQuery<User> pageUser);
}
