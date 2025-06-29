package com.qianwang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianwang.DTO.AdminDto;
import com.qianwang.common.ResponseResult;
import com.qianwang.pojo.Admin;

public interface AdminService extends IService<Admin> {


    ResponseResult adminLogin(AdminDto adminDto);
}
