package com.qianwang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianwang.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {


    @Select("select * from user where user_name = #{username}")
    com.qianwang.DTO.User getByUsername(String username);
}
