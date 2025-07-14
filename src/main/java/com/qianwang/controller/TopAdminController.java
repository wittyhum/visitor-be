package com.qianwang.controller;


import com.qianwang.DTO.AdminDto;
import com.qianwang.DTO.TopAdminDto;
import com.qianwang.VO.AdminVo;
import com.qianwang.VO.TopAdminVo;
import com.qianwang.VO.UserVo;
import com.qianwang.common.ResponseResult;
import com.qianwang.common.constant.JwtClaimsConstant;
import com.qianwang.common.properties.JwtProperties;
import com.qianwang.pojo.Admin;
import com.qianwang.pojo.TopAdmin;
import com.qianwang.service.TopAdminService;
import com.qianwang.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/visitor/topAdmin")
@Slf4j
public class TopAdminController {

    @Autowired
    private TopAdminService topAdminService;
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/login")
    public Result<TopAdminVo> login(@RequestBody TopAdminDto topAdminDto) throws AccountNotFoundException {
        log.info("审批员登录：{}" , topAdminDto);
        TopAdmin topAdmin = topAdminService.adminLogin(topAdminDto);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        //添加user ID 到声明中
        claims.put(JwtClaimsConstant.EMP_ID, topAdmin.getId());
        String token = JwtUtil.createJWT(
                // 密钥
                jwtProperties.getAdminSecretKey(),
                // 密钥有效期
                jwtProperties.getAdminTtl(),
                // 声明
                claims);

        TopAdminVo topAdminVo = TopAdminVo.builder()
                // 用户 ID
                .id(topAdmin.getId())
                // 用户名
                .adminName(topAdmin.getAdminName())
                // 令牌
                .token(token)
                .build();

        return Result.success(topAdminVo);

    }
}
