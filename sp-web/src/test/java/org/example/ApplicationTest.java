package org.example;

import org.example.sp.business.entity.User;
import org.example.sp.business.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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
        user.setName("liu");
        user.setEmail("254df@qq.com");
        user.setAge(18);
        int insert = mapper.insert(user);
        System.out.println(user.getId());
    }


}
