package com.qianwang.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianwang.DTO.RegisterDto;
import com.qianwang.DTO.UserDto;
import com.qianwang.common.ResponseResult;
import com.qianwang.enums.HttpCodeEnum;
import com.qianwang.mapper.UserMapper;
import com.qianwang.pojo.User;
import com.qianwang.service.UserService;
import com.qianwang.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Override
    public ResponseResult register(RegisterDto registerDto) {
        if (StringUtils.isBlank(registerDto.getUsername())){
            return ResponseResult.errorResult(HttpCodeEnum.REGISTER_USERNAME_NOTNULL);
        }
        if(StringUtils.isBlank(registerDto.getPassword())){
            return ResponseResult.errorResult(HttpCodeEnum.REGISTER_PASSWORD_NOTNULL);
        }
        User existUser = getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,registerDto.getUsername()));
        if(existUser!=null){
            return ResponseResult.errorResult(HttpCodeEnum.REGISTER_USERNAME_EXIST);
        }

        String salt =java.util.UUID.randomUUID().toString().replaceAll("-","").substring(0,3);

        String encryption = DigestUtils.md5DigestAsHex((registerDto.getPassword()+salt).getBytes());

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(encryption);
        user.setSalt(salt);
        user.setCreateTime(LocalDateTime.now());

        this.save(user);
        return ResponseResult.okResult("注册成功");

    }

    @Override
    public ResponseResult login(UserDto userDto) {
        if (StringUtils.isNotBlank(userDto.getUsername())&&StringUtils.isNotBlank(userDto.getPassword())){
            User dbUser = getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,userDto.getUsername()));
            if(dbUser == null){
                return ResponseResult.errorResult(HttpCodeEnum.DATA_NOT_EXIST);
            }

            String salt = dbUser.getSalt();
            String password = userDto.getPassword();
            String pswd = DigestUtils.md5DigestAsHex((password+salt).getBytes());
            if (!pswd.equals(dbUser.getPassword())){
                return ResponseResult.errorResult(HttpCodeEnum.LOGIN_PASSWORD_ERROR);
            }

            String jwt = JwtUtil.getToken(dbUser.getId());
            Map<String,Object> map = new HashMap<>();
            map.put("token",jwt);
            dbUser.setPassword("");
            dbUser.setSalt("");
            map.put("user",dbUser);
            return ResponseResult.okResult(map);
        }else {
            // 2. 游客登录
            Map<String, Object> map = new HashMap<>();
            map.put("token", JwtUtil.getToken(0L));
            return ResponseResult.okResult(map);
        }
    }
}
