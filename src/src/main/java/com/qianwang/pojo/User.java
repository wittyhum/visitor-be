package com.qianwang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //姓名
    @TableField("user_name")
    private String username;

    //密码
    @TableField("password")
    private String password;

    //盐
    @TableField("salt")
    private String salt;

    //预约时间
    @TableField("date_time")
    private LocalDateTime dateTime;

    //创建时间
    @TableField("create_time")
    private LocalDateTime createTime;

    //修改时间
    @TableField("update_time")
    private LocalDateTime updateTime;

    //修改人
    @TableField("update_name")
    private String updateName;
}
