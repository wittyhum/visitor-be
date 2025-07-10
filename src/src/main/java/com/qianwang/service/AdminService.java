package com.qianwang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianwang.DTO.AdminDto;
import com.qianwang.page.PageResultDto;
import com.qianwang.common.ResponseResult;
import com.qianwang.pojo.Admin;
import com.qianwang.page.PageResult;

public interface AdminService extends IService<Admin> {


    ResponseResult adminLogin(AdminDto adminDto);



    /**
     * 查询所有用户预约信息
     * @param pageResultDto
     * @return
     */
    PageResult getDateList(PageResultDto pageResultDto);


    /**
     * 根据状态判断是否审批，1为已审批，0为未审批
     * @param status
     * @param id
     * @return
     */
    void updateStatus(Integer status, Long id);
}
