package com.neepa.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffVO {
    private Integer id;
    private String name;
    private String password;
    private String sex;
    private long phone;
    private Integer departmentId;
    private String departmentName;

    public void setSex(short sex){
        this.sex= sex==1?"男":"女";
    }
}
