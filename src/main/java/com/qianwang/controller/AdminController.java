package com.qianwang.controller;

import com.qianwang.DTO.AdminDto;
import com.qianwang.DTO.User;
import com.qianwang.DTO.UserDto;
import com.qianwang.VO.AdminVo;
import com.qianwang.VO.UserVo;
import com.qianwang.common.constant.JwtClaimsConstant;
import com.qianwang.common.properties.JwtProperties;
import com.qianwang.page.PageResultDto;
import com.qianwang.common.ResponseResult;
import com.qianwang.pojo.Admin;
import com.qianwang.service.AdminService;
import com.qianwang.page.PageResult;
import com.qianwang.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

@Slf4j
@RestController
@RequestMapping("/visitor/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private JwtProperties jwtProperties;

//    @PostMapping("/login")
//    public ResponseResult adminLogin(@RequestBody AdminDto adminDto){
//        return adminService.adminLogin(adminDto);
//    }

    @PostMapping("/login")
    public Result<AdminVo> login(@RequestBody AdminDto adminDto) throws AccountNotFoundException {
        log.info("审批员登录：{}" , adminDto);
        Admin admin = adminService.adminLogin(adminDto);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        //添加user ID 到声明中
        claims.put(JwtClaimsConstant.EMP_ID, admin.getId());
        String token = JwtUtil.createJWT(
                // 密钥
                jwtProperties.getAdminSecretKey(),
                // 密钥有效期
                jwtProperties.getAdminTtl(),
                // 声明
                claims);

        AdminVo adminVo = AdminVo.builder()
                // 用户 ID
                .id(admin.getId())
                // 用户名
                .adminName(admin.getAdminName())
                // 令牌
                .token(token)
                .build();

        return Result.success(adminVo);

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
