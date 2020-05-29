package org.example.sp.business.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.sp.common.entity.User;
import org.example.sp.business.user.mapper.UserMapper;
import org.example.sp.business.user.service.IUserService;
import org.example.sp.common.entity.PageQuery;
import org.example.sp.common.util.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: yizl
 * @Date: 2020/5/19
 * @Description:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService  {

    @Autowired
    private UserMapper mapper;

    @Override
    public User getUserById(String id) {
        return mapper.selectById(id);
    }

    @Override
    public void insertUser(User user) {
        //前端传过来的密码(前端一般也会加密),后台进行加密
        String salt = ShiroUtil.getRandomSalt();
        String password = ShiroUtil.md5(user.getPassword(), salt);
        //数据库保存加密后的密码
        user.setPassword(password);
        mapper.insert(user);
    }

    @Override
    public void updateUserById(User user) {
        mapper.updateById(user);
    }

    @Override
    public IPage<User> queryUserPage(PageQuery<User> pageUser) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        User userParams = pageUser.getParams();
        if(userParams!=null){
            wrapper.like("user_name",userParams.getUserName());
        }
        return mapper.selectPage(pageUser.getPage(), wrapper);
    }

    @Override
    public User getByUserName(String userName) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",userName);
        return mapper.selectOne(wrapper);
    }


}
