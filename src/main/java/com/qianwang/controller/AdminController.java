package com.qianwang.controller;

import com.qianwang.DTO.AdminDto;
import com.qianwang.common.ResponseResult;
import com.qianwang.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/visitor")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/admin/login")
    public ResponseResult adminLogin(@RequestBody AdminDto adminDto){
        return adminService.adminLogin(adminDto);
    }
}
