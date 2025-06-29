package com.qianwang.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianwang.DTO.AdminDto;
import com.qianwang.common.ResponseResult;
import com.qianwang.enums.HttpCodeEnum;
import com.qianwang.mapper.AdminMapper;
import com.qianwang.pojo.Admin;
import com.qianwang.pojo.User;
import com.qianwang.service.AdminService;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService{


    @Override
    public ResponseResult adminLogin(AdminDto adminDto) {

        /*
         *    用户名：admin
         *    密码：123456
         * */

        //注册功能，把这段代码恢复，下面一段代码注释可以实现插入数据注册
//        String encryption = DigestUtils.md5DigestAsHex((adminDto.getPassword()).getBytes());
//        Admin admin = new Admin();
//        admin.setAdminName(adminDto.getUsername());
//        admin.setPassword(encryption);
//        admin.setCreateTime(LocalDateTime.now());
//        this.save(admin);
//        return ResponseResult.okResult("注册成功");


        if (StringUtils.isNotBlank(adminDto.getUsername())&&StringUtils.isNotBlank(adminDto.getPassword())){
            Admin dbAdmin = getOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getAdminName,adminDto.getUsername()));
            if (dbAdmin == null){
                return ResponseResult.errorResult(HttpCodeEnum.DATA_NOT_EXIST);
            }
            String pswd = DigestUtils.md5DigestAsHex((adminDto.getPassword()).getBytes());
            if (!pswd.equals(dbAdmin.getPassword())){
                return ResponseResult.errorResult(HttpCodeEnum.LOGIN_PASSWORD_ERROR);
            }

            String jwt = JwtUtil.getToken(dbAdmin.getId());
            Map<String,Object> map = new HashMap<>();
            map.put("token",jwt);
            dbAdmin.setPassword("");
            map.put("admin",dbAdmin);
            return ResponseResult.okResult(map);
        }else {
            return ResponseResult.errorResult(HttpCodeEnum.LOGIN_OPERATE);
        }
    }
}
