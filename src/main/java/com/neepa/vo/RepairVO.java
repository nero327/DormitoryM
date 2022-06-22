package com.neepa.vo;

import lombok.Data;

import java.util.Date;

@Data
public class RepairVO {
    private Integer id;
    private StudentVO student;
    private StaffVO staff;
    private String description;
    private float fee;
    private Integer status;
    private Date createTime;
    private Date updateTime;

}
