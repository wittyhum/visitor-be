package com.qianwang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianwang.DTO.ReservationDto;
import com.qianwang.page.PageResultDto;
import com.qianwang.pojo.Admin;
import com.qianwang.pojo.Reservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
    Page<ReservationDto> pageQuery(
            @Param("page") Page<ReservationDto> page,
            @Param("dto") PageResultDto pageResultDto
    );

    void updateStatus(Reservation reservation);

    @Select("select * from admin where admin_name = #{adminName}")
    Admin getByAdminName(String adminName);
}
