package org.example.sp.business.user.controller;




import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.sp.common.entity.User;
import org.example.sp.business.user.service.IUserService;
import org.example.sp.common.entity.PageQuery;
import org.example.sp.common.result.Result;
import org.example.sp.common.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Created by yangyibo on 16/12/29.
 *
 */

@Controller
public class WebSocketController {

    @Autowired
    private IUserService service;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RequestMapping(value = "/ws")
    public String ws(){
        return  "ws";
    }


    @MessageMapping("/welcome")//浏览器发送请求通过@messageMapping 映射/welcome 这个地址。
    @SendTo("/topic/getResponse")//服务器端有消息时,会订阅@SendTo 中的路径的浏览器发送消息。
    public Result<?> say(PageQuery<User> pageUser) throws Exception {
        IPage<User> userPage=service.queryUserPage(pageUser);
        return new Result<>(ResultEnum.SUCCESS,userPage);
    }


}
