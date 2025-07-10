package com.qianwang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianwang.DTO.RegisterDto;
import com.qianwang.DTO.UserDto;
import com.qianwang.common.ResponseResult;
import com.qianwang.pojo.Reservation;
import com.qianwang.pojo.User;

import javax.security.auth.login.AccountNotFoundException;

public interface UserService extends IService<User> {

    ResponseResult register(RegisterDto registerDto);


    com.qianwang.DTO.User login(UserDto userDto) throws AccountNotFoundException;
}
