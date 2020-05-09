package org.example.sp.web;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.example.sp.business.hello.world.entity.User;
import org.example.sp.business.hello.world.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
/*
 * 功能描述: 测试类
 * @Author: yizl
 * @Date: 2020/5/8 11:03
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ApplicationTest {

    @Autowired
    private UserMapper mapper;

    @Test
    public void findAll(){
        List<User> users = mapper.selectList(null);
        System.out.println(users);
    }

    @Test
    public void insertUser(){
        User user=new User();
        user.setName("addfasf");
        user.setEmail("58dxf@qq.com");
        user.setAge(22);
        int insert = mapper.insert(user);
        System.out.println(user.getId());
    }

    @Test
    public void uodateUserById(){
        User user=new User();
        user.setId("54211");
        user.setName("yi");
        user.setEmail("254df@qq.com");
        user.setAge(26);
        mapper.updateById(user);
        System.out.println(user.getId());
    }

   @Test
    public void uodateUser(){
        User user=new User();;
        UpdateWrapper<User> wrapper=new UpdateWrapper<>();
        wrapper.set("id","54211").set("age","22").eq("name","yi").eq("email","254df@qq.com");
        mapper.update(user,wrapper);
        System.out.println(user.getId());
    }

   @Test
    public void queryUserPage(){
        //这种分页方式，是内存分页，sql没有进行limit操作
       QueryWrapper<User> queryWrapper = new QueryWrapper();
       Page<User> userPage = new Page<>(2, 3);
       IPage<User> userIPage = mapper.selectPage(userPage, queryWrapper);
       //Integer count = mapper.selectCount(null);
       List<User> users = userIPage.getRecords();
       System.out.println(users);
   }


}
