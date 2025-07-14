package com.qianwang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianwang.DTO.TopAdminDto;
import com.qianwang.VO.TopAdminVo;
import com.qianwang.common.ResponseResult;
import com.qianwang.pojo.TopAdmin;

import javax.security.auth.login.AccountNotFoundException;

public interface TopAdminService extends IService<TopAdmin> {


    TopAdmin adminLogin(TopAdminDto topAdminDto) throws AccountNotFoundException;
}
