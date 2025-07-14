package com.qianwang.controller;


import com.qianwang.DTO.*;
import com.qianwang.VO.UserVo;
import com.qianwang.common.ResponseResult;
import com.qianwang.common.constant.JwtClaimsConstant;
import com.qianwang.common.properties.JwtProperties;
import com.qianwang.enums.HttpCodeEnum;
import com.qianwang.pojo.Reservation;
import com.qianwang.service.ReservationService;
import com.qianwang.service.UserService;
import com.qianwang.utils.AliOssUtil;
import com.qianwang.utils.JwtUtil;
import com.qianwang.utils.LoginContext;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/visitor/user")
public class UserController {


    @Autowired
    private UserService userService;
    @Autowired
    private ReservationService reservationService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private JwtProperties jwtProperties;


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
    public Result<UserVo> login(@RequestBody UserDto userDto) throws AccountNotFoundException {
        log.info("用户登录：{}" , userDto);
        User user =userService.login(userDto);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        //添加user ID 到声明中
        claims.put(JwtClaimsConstant.EMP_ID, user.getId());
        String token = JwtUtil.createJWT(
                // 密钥
                jwtProperties.getAdminSecretKey(),
                // 密钥有效期
                jwtProperties.getAdminTtl(),
                // 声明
                claims);

        UserVo userVo = UserVo.builder()
                // 用户 ID
                .id(user.getId())
                // 用户名
                .userName(user.getUsername())
                // 令牌
                .token(token)
                .build();

        return Result.success(userVo);

    }



    @PostMapping("/logout")
    public ResponseResult logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            // 将 Token 加入黑名单，有效期与 Token 剩余时间一致
            //从JWT token取出负载信息（包含token各种声明，用户ID，签发时间issuedAt，过期时间expiration，主题subject等）
            // 从声明中取出token过期时间，返回类型为date，再获取这个对象的时间戳，最后减去当前时间
//            long expiration = JwtUtil.getClaimsBody(token).getExpiration().getTime() - System.currentTimeMillis();
//            stringRedisTemplate.opsForValue().set("logout:" + token, "true", expiration, TimeUnit.MILLISECONDS);
            LoginContext.clear(); // 清空 ThreadLocal
            return ResponseResult.okResult("退出成功");
        }
        return ResponseResult.errorResult(HttpCodeEnum.LOGOUT_FAILED);
    }

    @PostMapping("/code")
    public ResponseResult<String> getCode(@RequestBody CodeDto codeDto) {
        return reservationService.getCode(codeDto);
    }




    /**
     * 用户预约功能
     * @param reservationDto
     * @return
     */
    @PostMapping("/date")
    public ResponseResult date(@RequestBody ReservationDto reservationDto,HttpServletRequest request){
        return reservationService.appointment(reservationDto, request);
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
    public ResponseResult<List<Reservation>> getMessage(HttpServletRequest request){
        String token = request.getHeader(jwtProperties.getAdminTokenName());
        Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(),token);
        Long userId = Long.valueOf(claims.get("id").toString());
        return reservationService.getMessage(userId);
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
