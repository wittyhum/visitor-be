package com.qianwang.controller;


import com.qianwang.DTO.RegisterDto;
import com.qianwang.DTO.UserDto;
import com.qianwang.common.ResponseResult;
import com.qianwang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/visitor")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseResult register(@RequestBody RegisterDto registerDto){
        return userService.register(registerDto);
    }


    @PostMapping("/login")
    public ResponseResult login(@RequestBody UserDto userDto){
        return userService.login(userDto);
    }
}
