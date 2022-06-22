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
public class Repair {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer studentId;
    private Integer staffId;
    private String description;
    private float fee;
    private Integer status;
    private Date createTime;
    private Date updateTime;

}
