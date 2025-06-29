package com.qianwang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianwang.DTO.RegisterDto;
import com.qianwang.DTO.UserDto;
import com.qianwang.common.ResponseResult;
import com.qianwang.pojo.User;

public interface UserService extends IService<User> {

    ResponseResult register(RegisterDto registerDto);

    ResponseResult login(UserDto userDto);

}
