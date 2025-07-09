package com.qianwang.controller;


import com.qianwang.DTO.RegisterDto;
import com.qianwang.DTO.ReservationDto;
import com.qianwang.DTO.UserDto;
import com.qianwang.common.ResponseResult;
import com.qianwang.enums.HttpCodeEnum;
import com.qianwang.pojo.Reservation;
import com.qianwang.service.ReservationService;
import com.qianwang.service.UserService;
import com.qianwang.utils.AliOssUtil;
import com.qianwang.utils.JwtUtil;
import com.qianwang.utils.LoginContext;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/visitor/user")
public class UserController {


    @Autowired
    private UserService userService;
    @Autowired
    private ReservationService reservationService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;


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
    public ResponseResult login(@RequestBody UserDto userDto, HttpServletResponse response) {
        ResponseResult result = userService.login(userDto);

        if (result.getCode() == HttpCodeEnum.SUCCESS.getCode()) {
            Object data = result.getData();
            if (data instanceof Map) {
                Map<String, Object> dataMap = (Map<String, Object>) data;
                String token = (String) dataMap.get("token");

                if (token != null) {
                    response.setHeader("Authorization", "Bearer " + token);
                } else {
                    // token 不存在的情况处理
                    return ResponseResult.errorResult(HttpCodeEnum.TOKEN_ERROR);
                }
            } else {
                // data 不是 Map 类型，说明格式不对
                return ResponseResult.errorResult(HttpCodeEnum.SYSTEM_ERROR, "登录失败：返回数据格式错误");
            }
        }

        return result;
    }



    @PostMapping("/logout")
    public ResponseResult logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            // 将 Token 加入黑名单，有效期与 Token 剩余时间一致
            //从JWT token取出负载信息（包含token各种声明，用户ID，签发时间issuedAt，过期时间expiration，主题subject等）
            // 从声明中取出token过期时间，返回类型为date，再获取这个对象的时间戳，最后减去当前时间
            long expiration = JwtUtil.getClaimsBody(token).getExpiration().getTime() - System.currentTimeMillis();
            stringRedisTemplate.opsForValue().set("logout:" + token, "true", expiration, TimeUnit.MILLISECONDS);
            LoginContext.clear(); // 清空 ThreadLocal
            return ResponseResult.okResult("退出成功");
        }
        return ResponseResult.errorResult(HttpCodeEnum.LOGOUT_FAILED);
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

    /**
     * 上传图片
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public String upload(MultipartFile file) throws IOException {
        return AliOssUtil.uploadImage(file);
    }

    /**
     * 查看自己预约的信息
     * @return
     */
    @GetMapping("/message")
    public ResponseResult<List<Reservation>> getMessage(){
        return reservationService.getMessage();
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
