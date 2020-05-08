package org.example.sp.business.hello.world.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloWorldController {


    @GetMapping("/world")
    public String helloWorld(){
        return "Hello World";
    }

}
