package com.neepa.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentVO {
    private String id;
    private String name;
    private String password;
    private String sex;
    private long phone;
    private Integer dormitoryId;
    private String floor;
    private String number;
    private Integer available;
    private Integer max;
    private Integer buildingId;
    private String buildingName;
    private Date createTime;
    private Date updateTime;

    public void setSex(short sex){
        this.sex= sex==1?"男":"女";
    }
}
