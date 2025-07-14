package com.qianwang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianwang.DTO.CodeDto;
import com.qianwang.DTO.ReservationDto;
import com.qianwang.common.ResponseResult;
import com.qianwang.pojo.Reservation;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ReservationService extends IService<Reservation> {


    /**
     * 用户预约功能
     *
     * @param reservationDto
     * @param request
     * @return
     */
    ResponseResult appointment(ReservationDto reservationDto, HttpServletRequest request);


    /**
     * 查看自己预约的信息
     * @return
     */
    ResponseResult<List<Reservation>> getMessage(Long userId);


    ResponseResult<String> getCode(CodeDto codeDto);
}
