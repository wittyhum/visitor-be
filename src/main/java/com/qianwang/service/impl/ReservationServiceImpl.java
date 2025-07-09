package com.qianwang.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianwang.DTO.ReservationDto;
import com.qianwang.common.ResponseResult;
import com.qianwang.enums.HttpCodeEnum;
import com.qianwang.enums.TarEnum;
import com.qianwang.mapper.ReservationMapper;
import com.qianwang.pojo.Reservation;
import com.qianwang.service.ReservationService;
import com.qianwang.utils.LoginContext;
import com.qianwang.utils.RegexUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.qianwang.utils.RedisConstants.LOGIN_CODE_KEY;
import static com.qianwang.utils.RedisConstants.LOGIN_CODE_TTL;


@Service
@Transactional
@Slf4j
public class ReservationServiceImpl extends ServiceImpl<ReservationMapper, Reservation> implements ReservationService {


    @Resource
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 用户预约功能
     * @param reservationDto
     * @return
     */
    @Override
    public ResponseResult appointment(ReservationDto reservationDto) {

        String phone = reservationDto.getPhone();

        // 1. 校验手机号格式
        if (RegexUtils.isPhoneInvalid(phone)) {
            return ResponseResult.errorResult(HttpCodeEnum.PHONE_FORMAT_ERROR);
        }

        String codeKey = LOGIN_CODE_KEY + phone;

        // 2. 获取 Redis 中的验证码和剩余时间
        String cacheCode = stringRedisTemplate.opsForValue().get(codeKey);
        Long remainingTtl = stringRedisTemplate.getExpire(codeKey, TimeUnit.SECONDS);

        String finalCode;

        // 3. 判断是否需要生成新验证码
        if (cacheCode == null || remainingTtl == null || remainingTtl <= 0) {
            // 验证码不存在 或 已过期 → 生成新验证码
            finalCode = RandomUtil.randomNumbers(6);
            stringRedisTemplate.opsForValue().set(codeKey, finalCode, 1, TimeUnit.MINUTES);
            log.info("新验证码已生成：{}", finalCode);
        } else {
            // 验证码仍然有效 → 使用已有验证码
            finalCode = cacheCode;
            log.info("使用已有验证码：{}，剩余时间：{} 秒", finalCode, remainingTtl);
        }

        // 4. 校验用户提交的验证码
        String voCode = reservationDto.getCode();
        if (voCode == null || !voCode.equals(finalCode)) {
            return ResponseResult.errorResult(HttpCodeEnum.CODE_ERROR);
        }

        // 5. 校验预约时间
        LocalDateTime appointmentTime = reservationDto.getDateTime();
        if (appointmentTime == null) {
            return ResponseResult.errorResult(HttpCodeEnum.APPOINTMENT_TIME_NOT_NULL);
        }
        if (appointmentTime.isBefore(LocalDateTime.now())) {
            return ResponseResult.errorResult(HttpCodeEnum.APPOINTMENT_TIME_INVALID);
        }
        LocalDateTime maxAppointmentDate = LocalDateTime.now().plusDays(14);
        if (appointmentTime.isAfter(maxAppointmentDate)) {
            return ResponseResult.errorResult(HttpCodeEnum.APPOINTMENT_TIME_TOO_FAR);
        }

        Reservation existingReservation = query().eq("phone", phone).one();
        if (existingReservation != null) {
            return ResponseResult.errorResult(HttpCodeEnum.PHONE_ALREADY_RESERVED);
        }

        //保存当前登录用户ID
        Long currentUserId = LoginContext.getCurrentUserId();

        // 6. 保存预约信息
        Reservation existReservation = new Reservation();
        BeanUtil.copyProperties(reservationDto, existReservation);
        existReservation.setUserId(currentUserId);
        existReservation.setCode(finalCode);
        existReservation.setStatus(0);
        existReservation.setDays(reservationDto.getDays());
        save(existReservation);



        // 7. 返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("message", "预约成功");
        result.put("appointmentTime", appointmentTime);
        result.put("phone", phone);
        result.put("code", finalCode); // 可选：将验证码返回给前端用于调试
        result.put("img",reservationDto.getImg());
        result.put("tar", TarEnum.getDescription(reservationDto.getTar()));
        result.put("voucher", reservationDto.isVoucher());


        return ResponseResult.okResult(result);
    }


    /**
     * 查看自己预约的信息
     * @return
     */
    @Override
    public ResponseResult<List<Reservation>> getMessage() {
        Long userId = LoginContext.getCurrentUserId();
        if (userId == null){
            return ResponseResult.errorResult(HttpCodeEnum.USER_NOT_LOGIN);
        }

        List<Reservation> reservations = list(Wrappers.<Reservation>lambdaQuery().eq(Reservation::getUserId,userId));
        return ResponseResult.okResult(reservations);
    }


}
