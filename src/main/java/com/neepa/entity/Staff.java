package com.neepa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Staff {
    @TableId(type = IdType.NONE)
    private Integer id;
    private String name;
    private String password;
    private short sex;
    private long phone;
    private Integer departmentId;
    private Date createTime;
    private Date updateTime;
}
