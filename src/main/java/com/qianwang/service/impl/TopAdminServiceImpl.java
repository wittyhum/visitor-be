package com.qianwang.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianwang.DTO.TopAdminDto;
import com.qianwang.common.ResponseResult;
import com.qianwang.enums.HttpCodeEnum;
import com.qianwang.mapper.TopAdminMapper;
import com.qianwang.pojo.TopAdmin;
import com.qianwang.service.TopAdminService;
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
public class TopAdminServiceImpl extends ServiceImpl<TopAdminMapper, TopAdmin> implements TopAdminService {
    @Override
    public ResponseResult topAdminLogin(TopAdminDto topAdminDto) {

        /*
        *   用户名：topAdmin
        *   密码：123456
        * */


        //注册功能，把这段代码恢复，下面一段代码注释可以实现插入数据注册
//        String encryption = DigestUtils.md5DigestAsHex((topAdminDto.getPassword()).getBytes());
//        TopAdmin topAdmin = new TopAdmin();
//        topAdmin.setAdminName(topAdminDto.getUsername());
//        topAdmin.setPassword(encryption);
//        topAdmin.setCreateTime(LocalDateTime.now());
//        this.save(topAdmin);
//        return ResponseResult.okResult("注册成功");


        if (StringUtils.isNotBlank(topAdminDto.getUsername())&&StringUtils.isNotBlank(topAdminDto.getPassword())){
            TopAdmin dbTopAdmin = getOne(Wrappers.<TopAdmin>lambdaQuery().eq(TopAdmin::getAdminName,topAdminDto.getUsername()));
            if (dbTopAdmin == null){
                return ResponseResult.errorResult(HttpCodeEnum.DATA_NOT_EXIST);
            }

            String pswd = DigestUtils.md5DigestAsHex((topAdminDto.getPassword()).getBytes());
            if (!pswd.equals(dbTopAdmin.getPassword())){
                return ResponseResult.errorResult(HttpCodeEnum.LOGIN_PASSWORD_ERROR);
            }

//            String jwt = JwtUtil.getToken(dbTopAdmin.getId());
            Map<String,Object> map = new HashMap<>();
//            map.put("token",jwt);
            dbTopAdmin.setPassword("");
            map.put("topAdmin",dbTopAdmin);
            return ResponseResult.okResult(map);
        }else {
            return ResponseResult.errorResult(HttpCodeEnum.LOGIN_OPERATE);
        }

    }
}
