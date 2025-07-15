package com.qianwang.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianwang.DTO.AdminDto;
import com.qianwang.DTO.ReservationDto;
import com.qianwang.common.constant.ExceptionConstant;
import com.qianwang.exception.PasswordErrorException;
import com.qianwang.page.PageResultDto;
import com.qianwang.common.ResponseResult;
import com.qianwang.enums.HttpCodeEnum;
import com.qianwang.mapper.AdminMapper;
import com.qianwang.pojo.Admin;
import com.qianwang.pojo.Reservation;
import com.qianwang.pojo.User;
import com.qianwang.service.AdminService;
import com.qianwang.utils.JwtUtil;
import com.qianwang.page.PageResult;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import javax.security.auth.login.AccountNotFoundException;
import java.util.HashMap;
import java.util.Map;


@Service
@Transactional
@Slf4j
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService{


    private final AdminMapper adminMapper;

    public AdminServiceImpl(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

//    @Override
//    public ResponseResult adminLogin(AdminDto adminDto) {
//
//        /*
//         *    用户名：admin
//         *    密码：123456
//         * */
//
//        //注册功能，把这段代码恢复，下面一段代码注释可以实现插入数据注册
////        String encryption = DigestUtils.md5DigestAsHex((adminDto.getPassword()).getBytes());
////        Admin admin = new Admin();
////        admin.setAdminName(adminDto.getUsername());
////        admin.setPassword(encryption);
////        admin.setCreateTime(LocalDateTime.now());
////        this.save(admin);
////        return ResponseResult.okResult("注册成功");
//
//
//        if (StringUtils.isNotBlank(adminDto.getUsername())&&StringUtils.isNotBlank(adminDto.getPassword())){
//            Admin dbAdmin = getOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getAdminName,adminDto.getUsername()));
//            if (dbAdmin == null){
//                return ResponseResult.errorResult(HttpCodeEnum.DATA_NOT_EXIST);
//            }
//            String pswd = DigestUtils.md5DigestAsHex((adminDto.getPassword()).getBytes());
//            if (!pswd.equals(dbAdmin.getPassword())){
//                return ResponseResult.errorResult(HttpCodeEnum.LOGIN_PASSWORD_ERROR);
//            }
//
////            String jwt = JwtUtil.getToken(dbAdmin.getId());
//            Map<String,Object> map = new HashMap<>();
////            map.put("token",jwt);
//            dbAdmin.setPassword("");
//            map.put("admin",dbAdmin);
//            return ResponseResult.okResult(map);
//        }else {
//            return ResponseResult.errorResult(HttpCodeEnum.LOGIN_OPERATE);
//        }
//    }


    @Override

    public Admin adminLogin(AdminDto adminDto) throws AccountNotFoundException {

        String adminName = adminDto.getUsername();
        String password = adminDto.getPassword();


        String pswd = DigestUtils.md5DigestAsHex(password.getBytes());

        Admin admin = adminMapper.getByAdminName(adminName);

        if(admin ==  null){
            //账号不存在
            throw new AccountNotFoundException(ExceptionConstant.ACCOUNT_NOT_FOUND);
        }
        if (!pswd.equals(admin.getPassword())){
            throw new PasswordErrorException(ExceptionConstant.PASSWORD_ERROR);
        }

        return admin;
    }

    /**
     * 查询所有用户预约信息
     * @param pageResultDto
     * @return
     */
    @Override
    public PageResult getDateList(PageResultDto pageResultDto) {
        // 构造 MP 分页对象
        Page<ReservationDto> page = new Page<>(pageResultDto.getPage(), pageResultDto.getPageSize());
        adminMapper.pageQuery(page, pageResultDto);

        // 封装结果返回
        return new PageResult(page.getTotal(), page.getRecords());
    }


    /**
     * 根据状态判断是否审批，1为已审批，0为未审批
     * @param status
     * @param id
     * @return
     */
    @Override
    public void updateStatus(Integer status, Long id) {
        Reservation reservation = Reservation.builder()
                .id(id)
                .status(status)
                .build();
        adminMapper.updateStatus(reservation);



    }


}
