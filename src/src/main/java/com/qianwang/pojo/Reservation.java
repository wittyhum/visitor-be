package com.qianwang.pojo;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("date")
public class Reservation {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    

    @TableField("name")
    private String name;

    @TableField("phone")
    private String phone;

    @TableField("code")
    private String code;

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
    private int tar;

    @TableField("voucher")
    private boolean voucher;

    @TableField("days")
    private String days;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("update_name")
    private String updateName;

}
