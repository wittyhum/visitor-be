package com.qianwang.page;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResultDto {


    private int page;

    private int pageSize;

    @TableField("name")
    private String name;



}
