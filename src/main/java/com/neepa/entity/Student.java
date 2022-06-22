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
public class Student {
    @TableId(type = IdType.NONE)
    private String id;
    private String name;
    private String password;
    private short sex;
    private long phone;
    private Integer dormitoryId;
    private Date createTime;
    private Date updateTime;

    public Student(String id, String name, String password, short sex, long phone, Integer dormitoryId) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.sex = sex;
        this.phone = phone;
        this.dormitoryId = dormitoryId;
    }
}
