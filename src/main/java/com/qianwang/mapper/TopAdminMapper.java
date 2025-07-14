package com.qianwang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianwang.pojo.TopAdmin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TopAdminMapper extends BaseMapper<TopAdmin> {

    @Select("select * from top_admin where admin_name = #{adminName}")
    TopAdmin getByAdminName(String adminName);
}
