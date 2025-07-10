package com.qianwang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianwang.DTO.TopAdminDto;
import com.qianwang.common.ResponseResult;
import com.qianwang.pojo.TopAdmin;

public interface TopAdminService extends IService<TopAdmin> {

    ResponseResult topAdminLogin(TopAdminDto topAdminDto);
}
