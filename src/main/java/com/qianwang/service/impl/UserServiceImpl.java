package com.qianwang.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianwang.DTO.RegisterDto;
import com.qianwang.DTO.UserDto;
import com.qianwang.common.ResponseResult;
import com.qianwang.common.constant.ExceptionConstant;
import com.qianwang.enums.HttpCodeEnum;
import com.qianwang.exception.PasswordErrorException;
import com.qianwang.mapper.UserMapper;
import com.qianwang.pojo.User;
import com.qianwang.service.UserService;
import com.qianwang.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;



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
    public com.qianwang.DTO.User login(UserDto userDto) throws AccountNotFoundException {
        String username = userDto.getUsername();
        String password = userDto.getPassword();

            User dbUser = getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,userDto.getUsername()));
            String salt = dbUser.getSalt();
            String pswd = DigestUtils.md5DigestAsHex((password+salt).getBytes());


        //1、根据用户名查询数据库中的数据
        com.qianwang.DTO.User user = userMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (user == null) {
            //账号不存在
            throw new AccountNotFoundException(ExceptionConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // TODO 后期需要进行md5加密，然后再进行比对
        if (!pswd.equals(user.getPassword())) {
            //密码错误
            throw new PasswordErrorException(ExceptionConstant.PASSWORD_ERROR);
        }

//        if (user.getStatus() == StatusConstant.DISABLE) {
//            //账号被锁定
//            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
//        }

        //3、返回实体对象
        return user;

    }


}
