package com.qianwang.DTO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {


    @TableField("name")
    private String name;

    @TableField("phone")
    private String phone;

    @TableField("code")
    private String code;


//    @DateTimeFormat(pattern = "yyyy-MM-dd") // 指定日期格式
    @JsonProperty("date_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("date_time")
    private LocalDateTime dateTime;

    @TableField("reason")
    private String reason;

    @TableField("address")
    private String address;

    @TableField("status")
    private Integer status;

    @TableField("img")
    private String img;

    @TableField("tar")
    private String tar;

    // 命名识别不到
    @TableField("voucher")
    private boolean voucher;

    // 命名识别不到
    @TableField("days")
    private String days;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("update_name")
    private String updateName;

}
