package com.qianwang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianwang.DTO.ReservationDto;
import com.qianwang.common.ResponseResult;
import com.qianwang.pojo.Reservation;

import java.util.List;

public interface ReservationService extends IService<Reservation> {


    /**
     * 用户预约功能
     * @param reservationDto
     * @return
     */
    ResponseResult appointment(ReservationDto reservationDto);


    /**
     * 查看自己预约的信息
     * @return
     */
    ResponseResult<List<Reservation>> getMessage();

}
