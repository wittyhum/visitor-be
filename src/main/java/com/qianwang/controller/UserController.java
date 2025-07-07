package com.qianwang.controller;


import com.qianwang.DTO.RegisterDto;
import com.qianwang.DTO.ReservationDto;
import com.qianwang.DTO.UserDto;
import com.qianwang.common.ResponseResult;
import com.qianwang.pojo.Reservation;
import com.qianwang.service.ReservationService;
import com.qianwang.service.UserService;
import com.qianwang.utils.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/visitor/user")
public class UserController {


    @Autowired
    private UserService userService;
    @Autowired
    private ReservationService reservationService;


    /**
     * 用户注册功能
     * @param registerDto
     * @return
     */
    @PostMapping("/register")
    public ResponseResult register(@RequestBody RegisterDto registerDto){
        return userService.register(registerDto);
    }

    /**
     * 用户登录功能
     * @param userDto
     * @return
     */
    @PostMapping("/login")
    public ResponseResult login(@RequestBody UserDto userDto){
        return userService.login(userDto);
    }


    /**
     * 用户预约功能
     * @param reservationDto
     * @return
     */
    @PostMapping("/date")
    public ResponseResult date(@RequestBody ReservationDto reservationDto){
        return reservationService.appointment(reservationDto);
    }


    @PostMapping("/upload")
    public String upload(MultipartFile file) throws IOException {
        return AliOssUtil.uploadImage(file);
    }



//    @PostMapping("/date")
//    public ResponseResult date(@RequestPart("reservationDto") ReservationDto reservationDto,
//                               @RequestParam(value = "img",required = false) MultipartFile img) throws IOException {
//
//        // 如果有上传文件，则上传并设置图片地址
//        if (img != null && !img.isEmpty()) {
//            String imgUrl = AliOssUtil.uploadImage(img);
//            reservationDto.setImg(imgUrl); // 设置到 DTO 中
//        }
//
//
//        return reservationService.appointment(reservationDto);
//    }

}
