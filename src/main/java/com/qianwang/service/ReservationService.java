package com.qianwang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianwang.DTO.ReservationDto;
import com.qianwang.common.ResponseResult;
import com.qianwang.pojo.Reservation;

public interface ReservationService extends IService<Reservation> {


    ResponseResult appointment(ReservationDto reservationDto);
}
