package com.qianwang.controller;

import com.qianwang.DTO.AdminDto;
import com.qianwang.page.PageResultDto;
import com.qianwang.common.ResponseResult;
import com.qianwang.service.AdminService;
import com.qianwang.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/visitor/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseResult adminLogin(@RequestBody AdminDto adminDto){
        return adminService.adminLogin(adminDto);
    }

    /**
     * 查询所有用户预约信息
     * @param pageResultDto
     * @return
     */
    @GetMapping("/list")
    public ResponseResult<PageResult> dateList(PageResultDto pageResultDto){
    PageResult pageResult = adminService.getDateList(pageResultDto);
    return ResponseResult.okResult(pageResult);
    }


    /**
     * 根据状态判断是否审批，1为已审批，0为未审批
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    public ResponseResult<String> updateStatus(@PathVariable Integer status, Long id){
        adminService.updateStatus(status,id);
        return ResponseResult.okResult("审批成功");
    }

}
