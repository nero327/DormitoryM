package com.neepa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private String id;
    private String name;
    private String password;
    private String sex;
    private long phone;
    private Integer buildingId;
    private Integer floor;
    private Integer number;
    public void setPassword(String password){
        if (password==null){
            this.password=this.id.substring(this.id.length()-6);
        }
        this.password = password;
    }
}