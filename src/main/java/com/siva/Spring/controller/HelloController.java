package com.siva.Spring.controller;

import com.siva.Spring.model.User;
import com.siva.Spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
public class HelloController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String diplayWelcome(){
        return "Welcome!";
    }

    @GetMapping("/{name}")
    public User findUserByName(@PathVariable String name){
        return userService.findUserByName(name);
    }
}
