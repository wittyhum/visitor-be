package com.qianwang.controller;


import com.qianwang.DTO.TopAdminDto;
import com.qianwang.common.ResponseResult;
import com.qianwang.service.TopAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/visitor")
public class TopAdminController {

    @Autowired
    private TopAdminService topAdminService;

    @PostMapping("/topAdmin/login")
    public ResponseResult topAdminLogin(@RequestBody TopAdminDto topAdminDto){
        return topAdminService.topAdminLogin(topAdminDto);
    }
}
